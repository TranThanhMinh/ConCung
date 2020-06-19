package com.example.myapplication.view.product

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.myapplication.R
import com.example.myapplication.model.RequestId
import com.example.myapplication.model.comment.ResquetComment
import com.example.myapplication.model.product.Promotion
import com.example.myapplication.util.Utility.Companion.currencyFormatter
import com.example.myapplication.util.Utility.Companion.id_user
import com.example.myapplication.util.Utility.Companion.name_user
import com.example.myapplication.util.Utility.Companion.url
import com.example.myapplication.view.adapter.CommentAdapter
import com.example.myapplication.view.adapter.ImageCaptureAdapter
import com.example.myapplication.view.adapter.PromotionAdapter
import com.example.myapplication.view.eventbus.CustomEvent
import com.example.myapplication.view.login.LoginAccountActivity
import com.example.myapplication.viewmodel.HomeViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.info_product.*
import kotlinx.android.synthetic.main.info_product_activity.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.io.ByteArrayOutputStream


class InfoProductActivity : AppCompatActivity(), CommentAdapter.ReplyComment, View.OnClickListener {
    private var homeViewModel: HomeViewModel? = null
    private var id: String? = null //id of product
    private var nameProduct: String? = null //name of product
    private var priceProduct: Int? = null //price of product
    private var imageProduct: String? = null //image of product
    private var capture: Int = 100
    private var show = false // show/hide description
    private var showComment = false // show/hide comment
    private var dialog: BottomSheetDialog? = null //dialog comment
    private var listImage: List<String> = ArrayList() //  list image capture of camera
    private var adapter: ImageCaptureAdapter? = null
    private var adapterComment: CommentAdapter? = null
    private var dialogImage: Dialog? = null
    private val TAKE_PICTURE = 1
    private var imageUri: Uri? = null
    private var imCapture: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.info_product_activity)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true);
        supportActionBar!!.title = getString(R.string.txt_infomation);

        //init
        init()
        checkPermission()
    }

    override fun onStart() {
        super.onStart()
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe
    fun onViewImage(event:CustomEvent){
        viewImage(event.url)
    }

    fun checkPermission() {
        // Với Android Level >= 23 bạn phải hỏi người dùng cho phép ghi dữ liệu vào thiết bị.
        if (Build.VERSION.SDK_INT >= 23) {
            // Kiểm tra quyền đọc/ghi dữ liệu vào thiết bị lưu trữ ngoài.
            val cameraPermission = ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.CAMERA)
            val readPermission = ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)
            val writePermission = ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
            if (writePermission != PackageManager.PERMISSION_GRANTED ||
                    readPermission != PackageManager.PERMISSION_GRANTED || cameraPermission != PackageManager.PERMISSION_GRANTED) {

                // Nếu không có quyền, cần nhắc người dùng cho phép.
                requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA),
                        1
                )
            }
        }
    }

    @SuppressLint("WrongConstant")
    fun init() {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        id = intent.getSerializableExtra("id") as String

        var layout = LinearLayoutManager(this)
        layout.orientation = LinearLayout.VERTICAL
        rlvComment.layoutManager = layout
        adapterComment = CommentAdapter(this, this)
        rlvComment.adapter = adapterComment

        val myDivider = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        myDivider.setDrawable(ContextCompat.getDrawable(this, R.drawable.cutm_dvdr)!!)
        rlvComment.addItemDecoration(myDivider)
        rlvComment.isNestedScrollingEnabled = false


        //information of product
        getIdProduct(id)
        //comment of product
        getComment()

        sizeLayout(400, getString(R.string.txt_show))


        tvCall.setOnClickListener(this)

        //show/hidden description
        btnShow.setOnClickListener(this)
        //show/hidden comment
        btnShowComment.setOnClickListener(this)
        //write comment
        editComment.setOnClickListener(this)
        //order
        llOrder.setOnClickListener(this)


        // Setup refresh listener which triggers new data loading

        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener { // Your code to refresh the list here.
            // Make sure you call swipeContainer.setRefreshing(false)
            // once the network request has completed successfully.
            //information of product
            getIdProduct(id)
            //comment of product
            getComment()

            swipeContainer.setRefreshing(false);
        }

        // Scheme colors for animation
        swipeContainer.setColorSchemeColors(
                getResources().getColor(android.R.color.holo_blue_bright),
                getResources().getColor(android.R.color.holo_green_light),
                getResources().getColor(android.R.color.holo_orange_light),
                getResources().getColor(android.R.color.holo_red_light)
        );
    }

    /**
     * function dialog comment
     */
    @SuppressLint("WrongConstant")
    fun diaLogComment(idComment: String) {
        if (id_user != null) {
            val view = layoutInflater.inflate(R.layout.bottom_sheet, null)
            dialog = BottomSheetDialog(this)
            dialog!!.setContentView(view)

            val rlvImage = dialog!!.findViewById<RecyclerView>(R.id.rlvImage)
            val btCancel = dialog!!.findViewById<TextView>(R.id.btCancel)
            val btnSend = dialog!!.findViewById<TextView>(R.id.btnSend)
            val tvNumberText = dialog!!.findViewById<TextView>(R.id.tvNumberText)
            imCapture = dialog!!.findViewById<ImageView>(R.id.imCapture)
            val editComment = dialog!!.findViewById<EditText>(R.id.editComment)

            dialog!!.show()

            // list image
            val layout = LinearLayoutManager(this)
            layout.orientation = LinearLayout.HORIZONTAL
            rlvImage!!.layoutManager = layout
            adapter = ImageCaptureAdapter(this)
            rlvImage!!.adapter = adapter

            // button cancel
            btCancel!!.setOnClickListener {
                dialog!!.cancel()
            }
            // button send
            btnSend!!.setOnClickListener {
                if (editComment!!.text.isNotEmpty()) {
                    insertComment(editComment!!.text.toString(), idComment)
                } else Toast.makeText(this, "Vui long viet binh luan cua ban", Toast.LENGTH_LONG).show()
            }
            // button capture camera
            imCapture!!.setOnClickListener {
              /*  val inten = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(inten, capture)*/

                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(intent, TAKE_PICTURE)
            }

            editComment!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {

                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (editComment.text.toString().length <= 4000) {
                        tvNumberText!!.text = "${editComment.text.length}/4000"
                    } else {
                        Toast.makeText(this@InfoProductActivity, "Nội dung đã quá 4000 ký tự", Toast.LENGTH_LONG).show()
                    }
                }

            })
        } else {
            val intent = Intent(this, LoginAccountActivity::class.java)
            startActivityForResult(intent, 1)
        }
    }

    /**
     * function get all comment
     */
    @SuppressLint("WrongConstant")
    fun getComment() {
        val requestId = RequestId()
        requestId.id = id!!
        homeViewModel!!.getComment(requestId).observe(this, Observer { item ->
            if (item != null) {
                btnShowComment.visibility = View.VISIBLE
                sizeLayoutComment(400, getString(R.string.txt_show))
                adapterComment!!.loadData(item.getComment())
            }
        })
    }

    /**
     * function insert comment
     * @param editComment: comment content
     * @param idComment: if = 0 new comment content else reply comment
     */
    fun insertComment(editComment: String, idComment: String) {
        val comment = ResquetComment()
        comment.setId(id)
        comment.setIdComment(idComment)
        comment.setIdUser(id_user)
        comment.setNameUser(name_user)
        comment.setTextComment(editComment)
        comment.setDateTime(System.currentTimeMillis().toString())
        homeViewModel!!.getInsertComment(comment).observe(this, Observer { item ->
            if (item != null) {
                if (item.getStatusCode().equals("200") || item.getStatusCode().equals("201")) {
                    //capture image
                    if (listImage != null && listImage.isNotEmpty()) {
                        for (i in listImage.indices) {
                            homeViewModel!!.imageUpLoad(listImage[i], item.getIdComment()!!).observe(this, Observer { item ->
                                if (item.getSuccess() == 200 && i == listImage.size - 1) {
                                    getComment()
                                    dialog!!.cancel()
                                    // urlPath = null
                                    listImage = ArrayList()
                                }
                            })
                        }
                    } else {
                        getComment()
                        dialog!!.cancel()
                    }
                }
            }
        })
    }


    /**
     * function show/hide description of product
     */
    fun sizeLayout(height: Int, text: String) {
        var linearLayout = tvDescription.layoutParams
        linearLayout.width = LinearLayout.LayoutParams.MATCH_PARENT
        linearLayout.height = height
        tvDescription.layoutParams = linearLayout
        btnShow.text = text
    }

    /**
     * function show/hide Comment of product
     */
    fun sizeLayoutComment(height: Int, text: String) {
        var linearLayout = rlvComment.layoutParams
        linearLayout.width = LinearLayout.LayoutParams.MATCH_PARENT
        linearLayout.height = height
        rlvComment.layoutParams = linearLayout
        btnShowComment.text = text
    }

    /**
     * function get info product via id
     * @param id: id of product
     */
    @SuppressLint("SetJavaScriptEnabled")
    private fun getIdProduct(id: String?) {
        val body = RequestId()
        body.id = id
        homeViewModel!!.getIdProduct(body).observe(this, Observer { list ->
            progress_bar.visibility = View.GONE
            if (list != null) {
                nameProduct = list.getData()[0].getName()
                priceProduct = list.getData()[0].getPrice()
                imageProduct = list.getData()[0].getImage()
                Picasso.with(this).load(list.getData()[0].getImage()).into(imageView)
                tvName.text = list.getData()[0].getName()
                tvPrice.text = currencyFormatter(list.getData()[0].getPrice()) + "đ"
                getPromotion(list.getPromotion()!!)

                tvDescription.requestFocus();
                tvDescription.settings.lightTouchEnabled = true;
                tvDescription.settings.javaScriptEnabled = true;
                tvDescription.settings.setGeolocationEnabled(true);
                tvDescription.isSoundEffectsEnabled = true;
                tvDescription.loadData(list.getData()[0].getDescription()!!, "text/html", null);
            } else {
                tvName.text = "không có thông tin"
            }

        })
    }


    /**
     * function get Promotion
     */
    @SuppressLint("WrongConstant")
    fun getPromotion(list: List<Promotion>) {
        var layout = LinearLayoutManager(this)
        layout.orientation = LinearLayout.VERTICAL
        rlvPromotion.layoutManager = layout
        val adapter = PromotionAdapter(this)
        rlvPromotion.adapter = adapter
        adapter.loadData(list)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == TAKE_PICTURE && resultCode == Activity.RESULT_OK) {
            val extras = data!!.extras
            val imageBitmap: Bitmap? = extras!!["data"] as Bitmap?
            val tempUri = getImageUri(this, imageBitmap!!)

            //urlPath = getRealPathFromURI(tempUri)!!
            (listImage as ArrayList).add(getRealPathFromURI(tempUri)!!)
            //  adapter!!.loadData(listImage)
            Log.e("tempUri", getRealPathFromURI(tempUri))

            try {
                imCapture!!.setImageBitmap(imageBitmap)
            } catch (e: Exception) {
                Toast.makeText(this, "Failed to load", Toast.LENGTH_SHORT)
                        .show()
                Log.e("Camera", e.toString())
            }

        } else if (requestCode == 1 && resultCode == 2) {
            url = data!!.getSerializableExtra("image") as String
            id_user = data!!.getSerializableExtra("id") as String
            name_user = data!!.getSerializableExtra("name_user") as String
        }
    }

    /**
     * function convert bitmap to uri
     */
    fun getImageUri(inContext: Context, inImage: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.PNG, 100, bytes)
        val path: String = MediaStore.Images.Media.insertImage(inContext.contentResolver, inImage, "Title", null)
        return Uri.parse(path)
    }

    /**
     * function convert uri to string (file path)
     */
    fun getRealPathFromURI(contentUri: Uri?): String? {
        val proj = arrayOf(MediaStore.Audio.Media.DATA)
        val cursor: Cursor = managedQuery(contentUri, proj, null, null, null)
        val column_index: Int = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)
        cursor.moveToFirst()
        return cursor.getString(column_index)
    }

    /**
     * function show dialog comment
     * @param idComment: id of comment
     */
    override fun commentReply(idComment: String) {
        diaLogComment(idComment)
    }

    /**
     * function show dialog cart
     */
    fun dialogCart() {
        val view = layoutInflater.inflate(R.layout.bottom_sheet_cart, null)
        dialog = BottomSheetDialog(this)
        dialog!!.setContentView(view)

        val tvName = dialog!!.findViewById<TextView>(R.id.tvName)
        val tvPrice = dialog!!.findViewById<TextView>(R.id.tvPrice)
        val tvMultiply = dialog!!.findViewById<TextView>(R.id.tvMultiply)
        val tvAmount = dialog!!.findViewById<TextView>(R.id.tvAmount)
        val imProduct = dialog!!.findViewById<ImageView>(R.id.imProduct)

        tvMultiply!!.text = "x${tvNumber.text}"
        tvName!!.text = nameProduct
        tvPrice!!.text = currencyFormatter(priceProduct!!)
        Picasso.with(this).load(imageProduct).into(imProduct)
        val amount = priceProduct!! * tvNumber.text.toString().toInt()
        tvAmount!!.text = " = ${currencyFormatter(amount)}"

        val layout = imProduct!!.layoutParams
        layout.height = Resources.getSystem().displayMetrics.widthPixels / 4 - Resources.getSystem().displayMetrics.widthPixels / 20
        imProduct!!.layoutParams = layout

        dialog!!.show()


    }

    fun viewImage(url:String) {
/*        dialogImage = Dialog(this)
        dialogImage!!.setContentView(R.layout.view_iamge)
        val imageView = dialogImage!!.findViewById<ImageView>(R.id.imageView)
        Picasso.with(this).load(url).into(imageView)
        dialogImage!!.show()*/

        val dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(this)
        val inflater: LayoutInflater = this.layoutInflater
        val dialogView: View = inflater.inflate(R.layout.view_iamge, null)
        dialogBuilder.setView(dialogView)

        val imageView = dialogView!!.findViewById<ImageView>(R.id.imageView)
        val imCancel = dialogView!!.findViewById<ImageView>(R.id.imCancel)
        Picasso.with(this).load(url).resize(Resources.getSystem().displayMetrics.widthPixels, Resources.getSystem().displayMetrics.heightPixels).into(imageView)
        val alertDialog: AlertDialog = dialogBuilder.create()
        alertDialog.show()

        imCancel.setOnClickListener {
            alertDialog.dismiss()
        }
    }

    /**
     * function handle click
     */
    override fun onClick(v: View?) {
        when (v) {
            tvCall -> {//call phone
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return
                }
                startActivity(Intent(Intent.ACTION_CALL, Uri.parse("tel:012345678")))
            }
            btnShow -> { //Description
                show = !show
                if (show) {
                    sizeLayout(LinearLayout.LayoutParams.WRAP_CONTENT, getString(R.string.txt_hide))
                } else {
                    sizeLayout(400, getString(R.string.txt_show))
                }
                llDescription.requestLayout();
            }
            btnShowComment -> {// Comment
                showComment = !showComment
                if (showComment) {
                    sizeLayoutComment(LinearLayout.LayoutParams.WRAP_CONTENT, getString(R.string.txt_hide))
                } else {
                    sizeLayoutComment(400, getString(R.string.txt_show))
                }
                llComment.requestLayout();
            }
            editComment -> {
                diaLogComment("0") // 0  new comment content
            }

            llOrder -> {
                if (id_user != null) {
                    dialogCart()
                } else {
                    val intent = Intent(this, LoginAccountActivity::class.java)
                    startActivityForResult(intent, 1)
                }
            }
        }
    }
}