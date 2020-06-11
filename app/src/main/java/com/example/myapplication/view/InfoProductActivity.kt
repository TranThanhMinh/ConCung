package com.example.myapplication.view

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.model.RequestId
import com.example.myapplication.model.comment.Comment
import com.example.myapplication.model.comment.ResquetComment
import com.example.myapplication.model.product.Promotion
import com.example.myapplication.util.Utility
import com.example.myapplication.util.Utility.Companion.currencyFormatter
import com.example.myapplication.util.Utility.Companion.id_user
import com.example.myapplication.util.Utility.Companion.name_user
import com.example.myapplication.util.Utility.Companion.url
import com.example.myapplication.view.adapter.CommentAdapter
import com.example.myapplication.view.adapter.ImageCaptureAdapter
import com.example.myapplication.view.adapter.PromotionAdapter
import com.example.myapplication.viewmodel.HomeViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.bottom_sheet.*
import kotlinx.android.synthetic.main.info_product.*
import kotlinx.android.synthetic.main.info_product.editComment
import java.io.ByteArrayOutputStream


class InfoProductActivity : AppCompatActivity(), CommentAdapter.ReplyComment {
    private var homeViewModel: HomeViewModel? = null
    private var id: String? = null
    private var capture: Int = 100
    private var show = false
    private var urlPath: String? = null
    private var dialog: BottomSheetDialog? = null
    private var listImage: List<String> = ArrayList()
    private var adapter: ImageCaptureAdapter? = null
    private var adapterComment: CommentAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.info_product_activity)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true);
        supportActionBar!!.title = getString(R.string.txt_infomation);

        //init
        init()
    }

    @SuppressLint("WrongConstant")
    fun init() {
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






        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)

        id = intent.getSerializableExtra("id") as String

        var layout = LinearLayoutManager(this)
        layout.orientation = LinearLayout.VERTICAL
        rlvComment.layoutManager = layout
        adapterComment = CommentAdapter(this, this)
        rlvComment.adapter = adapterComment


        //information of product
        getIdProduct(id)
        //comment of product
        getComment()

        sizeLayout(400, getString(R.string.txt_show))

        //show/hidden description
        btnShow.setOnClickListener {
            show = !show
            if (show) {
                sizeLayout(LinearLayout.LayoutParams.WRAP_CONTENT, getString(R.string.txt_hide))
            } else {
                sizeLayout(400, getString(R.string.txt_show))
            }
            llDescription.requestLayout();
        }

        editComment.setOnClickListener {
            diaLogComment("0")
        }
    }

    @SuppressLint("WrongConstant")
    fun diaLogComment(idComment:String) {
        if (id_user != null) {
            val view = layoutInflater.inflate(R.layout.bottom_sheet, null)
            dialog = BottomSheetDialog(this)
            dialog!!.setContentView(view)

            val rlvImage = dialog!!.findViewById<RecyclerView>(R.id.rlvImage)
            val btCancel = dialog!!.findViewById<TextView>(R.id.btCancel)
            val btnSend = dialog!!.findViewById<TextView>(R.id.btnSend)
            val tvNumberText = dialog!!.findViewById<TextView>(R.id.tvNumberText)
            val imCapture = dialog!!.findViewById<ImageView>(R.id.imCapture)
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
                    insertComment(editComment!!.text.toString(),idComment)
                } else Toast.makeText(this, "Vui long viet binh luan cua ban", Toast.LENGTH_LONG).show()
            }
            // button capture camera
            imCapture!!.setOnClickListener {
                val inten = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(inten, capture)
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

    @SuppressLint("WrongConstant")
    fun getComment() {
        val requestId = RequestId()
        requestId.id = id!!
        homeViewModel!!.getComment(requestId).observe(this, Observer { item ->
            if (item != null) {
                adapterComment!!.loadData(item.getComment())
            }
        })
    }

    // comment
    fun insertComment(editComment: String,idComment:String) {
        val comment = ResquetComment()
        comment.setId(id)
        comment.setIdComment(idComment)
        comment.setIdUser(id_user)
        comment.setNameUser(name_user)
        comment.setTextComment(editComment)
        comment.setDateTime(System.currentTimeMillis().toString())
        homeViewModel!!.getInsertComment(comment).observe(this, Observer { item ->
            if (item != null) {
                if (item.getStatusCode().equals("200")) {
                    //capture image
                    if (urlPath != null && urlPath!!.isNotEmpty()) {
                        homeViewModel!!.imageUpLoad(urlPath!!, item.getIdComment()!!).observe(this, Observer { item ->
                            if (item.getSuccess() == 200) {
                                getComment()
                                dialog!!.cancel()
                            }
                        })
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
                Picasso.with(this).load(list.getData()[0].getImage()).into(imageView)
                tvName.text = list.getData()[0].getName()
                tvPrice.text = currencyFormatter(list.getData()[0].getPrice()!!) + "đ"
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
        if (requestCode == capture && resultCode == Activity.RESULT_OK) {
            val extras = data!!.extras
            val imageBitmap: Bitmap? = extras!!["data"] as Bitmap?
            val tempUri = getImageUri(this, imageBitmap!!)

            urlPath = getRealPathFromURI(tempUri)!!
            //  (listImage as ArrayList).add(getRealPathFromURI(tempUri)!!)
            //  adapter!!.loadData(listImage)
            Log.e("tempUri", getRealPathFromURI(tempUri))
        } else if (requestCode == 1 && resultCode == 2) {
            url = data!!.getSerializableExtra("image") as String
            id_user = data!!.getSerializableExtra("id") as String
        }
    }

    fun getImageUri(inContext: Context, inImage: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path: String = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null)
        return Uri.parse(path)
    }

    fun getRealPathFromURI(contentUri: Uri?): String? {
        val proj = arrayOf(MediaStore.Audio.Media.DATA)
        val cursor: Cursor = managedQuery(contentUri, proj, null, null, null)
        val column_index: Int = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)
        cursor.moveToFirst()
        return cursor.getString(column_index)
    }

    override fun commentReply(item: Comment) {
        diaLogComment(item.getIdComment()!!)
    }
}