package com.example.concung.view.product

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
import android.view.*
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.MenuItemCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.concung.R
import com.example.concung.dagger.Component.DaggerProductComponent
import com.example.concung.dagger.Component.ProductComponent
import com.example.concung.data.Cart
import com.example.concung.data.ProductWatched
import com.example.concung.model.comment.ResquetComment
import com.example.concung.model.home.RequestId
import com.example.concung.model.product.Product
import com.example.concung.model.product.Promotion
import com.example.concung.util.Utility
import com.example.concung.util.Utility.Companion.currencyFormatter
import com.example.concung.util.Utility.Companion.id_user
import com.example.concung.util.Utility.Companion.name_user
import com.example.concung.util.Utility.Companion.url
import com.example.concung.view.InterfaceClick
import com.example.concung.view.adapter.CommentAdapter
import com.example.concung.view.adapter.ImageCaptureAdapter
import com.example.concung.view.adapter.PromotionAdapter
import com.example.concung.view.cart.CartFragment
import com.example.concung.view.eventbus.CustomEvent
import com.example.concung.view.login.LoginActivity
import com.example.concung.viewmodel.HomeViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.info_product.*
import kotlinx.android.synthetic.main.info_product_activity.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.io.ByteArrayOutputStream
import javax.inject.Inject


class InfoProductFragment : Fragment(), CommentAdapter.ReplyComment, View.OnClickListener {
    private var homeViewModel: HomeViewModel? = null
    var id: String? = null //id of product
    var number: Int = 1 //id of product
    var nameProduct: String? = null //name of product
    var priceProduct: Int? = null //price of product
    var imageProduct: String? = null //image of product
    var love: Boolean = false //love of product
    var listProduct: List<Product>? = null//love of product
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

    private var cartExists: Cart? = null

    lateinit var productComponent: ProductComponent
    lateinit var back: InterfaceClick.OnBack

    @Inject
    lateinit var cart: Cart



    override fun onAttach(context: Context?) {
        super.onAttach(context)
        back = context as InterfaceClick.OnBack

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.info_product_activity,container,false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = activity as AppCompatActivity?
        activity!!.setSupportActionBar(toolbar)
        activity!!.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        activity!!.supportActionBar!!.title = ""
        setHasOptionsMenu(true)

        init()

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
    fun onViewImage(event: CustomEvent) {
        viewImage(event.url)
    }


    @SuppressLint("WrongConstant")
    fun init() {
        //progressbar
        progress_bar.visibility = View.VISIBLE
        productComponent = DaggerProductComponent.builder().build()
        productComponent.inject(this)

        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        id = activity!!.intent.getSerializableExtra("id") as String

        val love_ = activity!!.intent.getSerializableExtra("love") as String
        love = love_ == "1"
        imLove.isSelected = love

        var layout = LinearLayoutManager(context)
        layout.orientation = LinearLayout.VERTICAL
        rlvComment.layoutManager = layout
        adapterComment = CommentAdapter(context!!, this)
        rlvComment.adapter = adapterComment

        val myDivider = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        myDivider.setDrawable(ContextCompat.getDrawable(context!!, R.drawable.cutm_dvdr)!!)
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

        btnPlus.setOnClickListener(this)

        btnMinus.setOnClickListener(this)

        imLove.setOnClickListener(this)


        // Setup refresh listener which triggers new data loading

        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener { // Your code to refresh the list here.
            // Make sure you call swipeContainer.setRefreshing(false)
            // once the network request has completed successfully.
            //information of product
            getIdProduct(id)
            //comment of product
            getComment()

            swipeContainer.isRefreshing = false;
        }

        // Scheme colors for animation
        swipeContainer.setColorSchemeColors(
                resources.getColor(android.R.color.holo_blue_bright),
                resources.getColor(android.R.color.holo_green_light),
                resources.getColor(android.R.color.holo_orange_light),
                resources.getColor(android.R.color.holo_red_light)
        )

        homeViewModel!!.getIdCart(id!!).observe(this, Observer { item ->
            Log.e("MInh", "kkkk")
            if (item != null) {
                cartExists = item
                Log.e("MInh", "isNotEmpty")
            } else {
                cartExists = null
                Log.e("MInh", "isEmpty")
            }
        })


    }

    /**
     * function dialog comment
     */
    @SuppressLint("WrongConstant")
    fun diaLogComment(idComment: String) {
        if (id_user != null) {
            val view = layoutInflater.inflate(R.layout.bottom_sheet, null)
            dialog = BottomSheetDialog(context!!)
            dialog!!.setContentView(view)

            val rlvImage = dialog!!.findViewById<RecyclerView>(R.id.rlvImage)
            val btCancel = dialog!!.findViewById<TextView>(R.id.btCancel)
            val btnSend = dialog!!.findViewById<TextView>(R.id.btnSend)
            val tvNumberText = dialog!!.findViewById<TextView>(R.id.tvNumberText)
            imCapture = dialog!!.findViewById<ImageView>(R.id.imCapture)
            val editComment = dialog!!.findViewById<EditText>(R.id.editComment)

            dialog!!.show()

            // list image
            val layout = LinearLayoutManager(context!!)
            layout.orientation = LinearLayout.HORIZONTAL
            rlvImage!!.layoutManager = layout
            adapter = ImageCaptureAdapter(context!!)
            rlvImage!!.adapter = adapter

            // button cancel
            btCancel!!.setOnClickListener {
                dialog!!.cancel()
            }
            // button send
            btnSend!!.setOnClickListener {
                if (editComment!!.text.isNotEmpty()) {
                    insertComment(editComment!!.text.toString(), idComment)
                } else Toast.makeText(context, getString(R.string.txt_create_comment), Toast.LENGTH_LONG).show()
            }
            // button capture camera
            imCapture!!.setOnClickListener {
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
                        Toast.makeText(context, "Nội dung đã quá 4000 ký tự", Toast.LENGTH_LONG).show()
                    }
                }

            })
        } else {
            val intent = Intent(context, LoginActivity::class.java)
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
                listProduct = list.getData()
                nameProduct = list.getData()[0].getName()
                priceProduct = list.getData()[0].getPrice()
                imageProduct = list.getData()[0].getImage()
                Picasso.with(context).load(list.getData()[0].getImage()).into(imageView)
                tvName.text = list.getData()[0].getName()
                tvPrice.text = currencyFormatter(list.getData()[0].getPrice()) + resources.getString(R.string.txt_value)
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
        var layout = LinearLayoutManager(context)
        layout.orientation = LinearLayout.VERTICAL
        rlvPromotion.layoutManager = layout
        val adapter = PromotionAdapter(context!!)
        rlvPromotion.adapter = adapter
        adapter.loadData(list)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater!!.inflate(R.menu.menu_cart, menu)
        val item = menu!!.findItem(R.id.item_cart)
        MenuItemCompat.setActionView(item, R.layout.icon_cart)
        val notifCount = MenuItemCompat.getActionView(item) as RelativeLayout
        val tvNumber = notifCount.findViewById<TextView>(R.id.tvNumber)
        val imCart = notifCount.findViewById<ImageView>(R.id.imCart)
        imCart.setOnClickListener {
            if (id_user != null) {
                val fm = activity!!.supportFragmentManager
                Utility.replaceProductFragment(fm, CartFragment())
            } else {
                val intent = Intent(context, LoginActivity::class.java)
                startActivityForResult(intent, 1)
            }
        }
        tvNumber.text = "1"
        super.onCreateOptionsMenu(menu, inflater)
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                back.onBack()
            }
            R.id.item_home -> {
                back.onBackHome()
            }
            R.id.item_cart -> {
                if (id_user != null) {
                    val fm = activity!!.supportFragmentManager
                    Utility.replaceProductFragment(fm, CartFragment())
                } else {
                    val intent = Intent(context, LoginActivity::class.java)
                    startActivityForResult(intent, 1)
                }
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
            val tempUri = getImageUri(context!!, imageBitmap!!)

            //urlPath = getRealPathFromURI(tempUri)!!
            (listImage as ArrayList).add(getRealPathFromURI(tempUri)!!)
            //  adapter!!.loadData(listImage)
            Log.e("tempUri", getRealPathFromURI(tempUri))

            try {
                imCapture!!.setImageBitmap(imageBitmap)
            } catch (e: Exception) {
                Toast.makeText(context, "Failed to load", Toast.LENGTH_SHORT)
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
        val cursor: Cursor = activity!!.managedQuery(contentUri, proj, null, null, null)
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
        dialog = BottomSheetDialog(context!!)
        dialog!!.setContentView(view)

        val tvName = dialog!!.findViewById<TextView>(R.id.tvName)
        val tvPrice = dialog!!.findViewById<TextView>(R.id.tvPrice)
        val tvMultiply = dialog!!.findViewById<TextView>(R.id.tvMultiply)
        val tvAmount = dialog!!.findViewById<TextView>(R.id.tvAmount)
        val tvDelete = dialog!!.findViewById<TextView>(R.id.tvDelete)
        val imProduct = dialog!!.findViewById<ImageView>(R.id.imProduct)
        val btnCart = dialog!!.findViewById<TextView>(R.id.btnCart)

        tvDelete!!.visibility = View.GONE

        tvMultiply!!.text = "x${tvNumber.text}"
        tvName!!.text = nameProduct
        tvPrice!!.text = currencyFormatter(priceProduct!!)
        Picasso.with(context).load(imageProduct).into(imProduct)
        val amount = priceProduct!! * tvNumber.text.toString().toInt()
        tvAmount!!.text = " = ${currencyFormatter(amount)}" + resources.getString(R.string.txt_value)

        val layout = imProduct!!.layoutParams
        layout.height = Resources.getSystem().displayMetrics.widthPixels / 4 - Resources.getSystem().displayMetrics.widthPixels / 20
        imProduct!!.layoutParams = layout

        dialog!!.show()

        cart.id = id
        cart.name = nameProduct
        cart.price = priceProduct.toString()
        cart.image = imageProduct
        cart.number = tvNumber.text.toString().toInt()


        if (cartExists != null) {
            cart.number = cartExists!!.number!! + cart.number!!
            homeViewModel!!.updateCart(cart)
        } else {
            homeViewModel!!.insertCart(cart)
        }
        btnCart!!.setOnClickListener {
            dialog!!.cancel()
            val fm = activity!!.supportFragmentManager
            Utility.replaceProductFragment(fm, CartFragment())
        }

    }

    fun viewImage(url: String) {
        val dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(context)
        val inflater: LayoutInflater = this.layoutInflater
        val dialogView: View = inflater.inflate(R.layout.view_iamge, null)
        dialogBuilder.setView(dialogView)

        val imageView = dialogView!!.findViewById<ImageView>(R.id.imageView)
        val imCancel = dialogView!!.findViewById<ImageView>(R.id.imCancel)
        Picasso.with(context).load(url).resize(Resources.getSystem().displayMetrics.widthPixels, Resources.getSystem().displayMetrics.heightPixels).into(imageView)
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
                if (ActivityCompat.checkSelfPermission(context!!, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
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

            imLove -> {
                love = !love
                v!!.isSelected = love

                val productWatched = ProductWatched()
                productWatched.id = listProduct!![0].getId().toString()
                productWatched.name = listProduct!![0].getName()
                productWatched.price = listProduct!![0].getPrice().toString()
                productWatched.image = listProduct!![0].getImage()
                if (love)
                productWatched.love = "1"
                else   productWatched.love = "0"

                homeViewModel!!.productExist(listProduct!![0].getId().toString()).observe(this, Observer {
                    list ->
                    if(this.lifecycle.currentState== Lifecycle.State.RESUMED) {
                        if (list.isNotEmpty()) {
                            Log.e("MInh3", "isNotEmpty")
                            homeViewModel!!.updateProduct(productWatched)
                        } else {
                            homeViewModel!!.insertProduct(productWatched)
                            Log.e("MInh3", "isEmpty")
                        }
                    }
                })
            }

            llOrder -> {
                if (id_user != null) {
                    dialogCart()
                } else {
                    val intent = Intent(context, LoginActivity::class.java)
                    startActivityForResult(intent, 1)
                }
            }
            btnPlus -> {
                if (number > 1) {
                    number -= 1
                    tvNumber.text = number.toString()
                }
            }
            btnMinus -> {
                number += 1
                tvNumber.text = number.toString()
            }
        }
    }


}