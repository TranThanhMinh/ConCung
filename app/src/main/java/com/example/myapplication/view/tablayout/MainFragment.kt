package com.example.myapplication.view.tablayout

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.data.Student
import com.example.myapplication.model.Example
import com.example.myapplication.util.RxSearch
import com.example.myapplication.view.InterfaceClick
import com.example.myapplication.view.adapter.AdapterStudent
import com.example.myapplication.viewmodel.ExampleViewModel
import com.example.myapplication.viewmodel.StudentViewModel
import rx.android.schedulers.AndroidSchedulers;
import kotlinx.android.synthetic.main.fragment_main.*

import kotlin.collections.ArrayList
import java.util.concurrent.TimeUnit



class MainFragment : Fragment(), View.OnClickListener, InterfaceClick.Adapter {

    private lateinit var exampleViewModel: ExampleViewModel
    private lateinit var studentViewModel: StudentViewModel
    private lateinit var ex: Observer<Example>
    private lateinit var listStudent: List<Student>
    private lateinit var adapter: AdapterStudent
    private lateinit var observerList:  Observer<List<Student>>
    private lateinit var observerLoadData:  Observer<List<Student>>

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun clickChooseDelete(check: Boolean) {
        btnDelete.isEnabled = check
        when(check){
           true-> btnDelete.background = context!!.resources.getDrawable(R.drawable.boder_insert)
           false-> btnDelete.background = context!!.resources.getDrawable(R.drawable.border_false)
        }
    }

    override fun clickItem(student: Student) {
        editId.isEnabled = !student.viewItem
        when(student.viewItem){
            false->resetEditText()
            else->{
                editId.setText(student.mIdStudent.toString())
                editName.setText( student.mNameStudent.toString())
                editClass.setText(student.mClass.toString())
                editNumber.setText(student.mNumber.toString())
                btnInsert.text = resources.getString(R.string.update)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onClick(v: View?) {
        when (v) {
            btnInsert -> when {
                editId.text.toString().isEmpty() -> editId.error = "Please enter Id Name"
                editName.text.toString().isEmpty() -> editName.error = "Please enter Name"
                editClass.text.toString().isEmpty() -> editClass.error = "Please enter Class"
                editNumber.text.toString().isEmpty() -> editClass.error = "Please enter Class"
                else -> {
                    if ( btnInsert.text == resources.getString(R.string.insert)) {
                        if (!checkExist(editId.text.toString())) {
                            studentViewModel.insert(setStudent())
                            (listStudent as ArrayList).add(setStudent())
                            adapter.loadData(listStudent)
                            resetEditText()
                        } else showToast("Ma ID: ${editId.text} nay exist")
                    }else{
                        studentViewModel.update(setStudent())
                        getAllStudent()
                        btnInsert.text = resources.getString(R.string.insert)
                        resetEditText()
                    }
                }
            }
            //delete  student when chek = true
            btnDelete -> {
                for (i in 0..listStudent.size - 1) {
                    when (listStudent[i].check) {
                        true -> studentViewModel.delete(listStudent[i])
                    }
                }
                getAllStudent()
                resetEditText()
                clickChooseDelete(false)
            }
            imShowOrHide -> when (ctlIdName.isShown) {
                true -> showOrHide(View.GONE, R.drawable.ic_show)
                else -> showOrHide(View.VISIBLE, R.drawable.ic_hide)
            }
        }

    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun showOrHide(view: Int, image:Int){
        ctlIdName.visibility = view
        ctlName.visibility = view
        imShowOrHide.setImageDrawable(context!!.resources.getDrawable(image))
    }

    fun setStudent():Student{
        val student = Student()
        student.mIdStudent = editId.text.toString()
        student.mNameStudent = editName.text.toString()
        student.mClass = editClass.text.toString()
        student.mNumber = editNumber.text.toString().toInt()
        return student
    }

    /**
     * check exist of student
     * @param id: id of student
     */
    fun checkExist(id: String): Boolean {
        for (Student in listStudent) {
            if (Student.mIdStudent!!.equals(id)) {
                return true
            }
        }
        return false
    }

    fun resetEditText(){
        editId.text = null
        editName.text = null
        editClass.text = null
        editNumber.text = null
        btnInsert.text = resources.getString(R.string.insert)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        exampleViewModel = ViewModelProviders.of(this).get(ExampleViewModel::class.java)
        studentViewModel = ViewModelProviders.of(this).get(StudentViewModel::class.java)
        ex = Observer { example ->
         /*   tvIdName2.text = example.getAd()!!.getCompany()
            tvName2.text = example.getAd()!!.getText()*/
        }


        exampleViewModel.getExample().observe(this, ex)

        observerList =  Observer { list ->
            if(list.isNotEmpty()){
                showToast("ID Name ${editId.text} exist")
            }else{
                val student = Student()
                student.mIdStudent = editId.text.toString()
                student.mNameStudent = editName.text.toString()
                student.mClass = editClass.text.toString()
                studentViewModel.insert(student)
                resetEditText()
                (listStudent as ArrayList).add(student)
                adapter.loadData(listStudent)

            }
        }

        observerLoadData =  Observer { list ->
            this.listStudent = list
            adapter.loadData(list)

        }

        UI()
    }

    fun showToast(text:String){
        Toast.makeText(context, text, Toast.LENGTH_LONG).show()
    }


    /**
     * init layout
     */
    @SuppressLint("WrongConstant")
    fun UI() {
        adapter = AdapterStudent(context!!,this)
        val lay = LinearLayoutManager(context)
        lay.orientation = LinearLayout.VERTICAL
        rcvList.layoutManager = lay
        rcvList.adapter = adapter
        getAllStudent()

        btnInsert.setOnClickListener(this)
        btnDelete.setOnClickListener(this)
        imShowOrHide.setOnClickListener(this)
   /*     for (i in 31.. 100){
            val student = Student()
            student.mIdStudent = i.toString()
            student.mNameStudent = "Tran Thanh Minh"
            student.mClass =  "Lop 3"
            studentViewModel.insert(student)
        }*/

        RxSearch.fromSearchView(search_bar)
                .debounce(2000,TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { query: String? ->
                    when (query) {
                        null -> {
                            adapter.loadData(listStudent)
                        }
                        else -> {
                            val list = ArrayList<Student>()
                            for (Student in listStudent) {
                                if (Student.mNameStudent!!.toLowerCase().contains(query.toLowerCase())
                                        || Student.mIdStudent!!.toLowerCase().contains(query.toLowerCase())
                                        || Student.mClass!!.toLowerCase().contains(query.toLowerCase()))
                                    list.add(Student)
                            }
                             adapter.loadData(list)
                        }
                    }
                }
    }

    /**
     * get all student
     */
    fun getAllStudent() {
        listStudent = ArrayList()
        studentViewModel.getAll().observe(this,observerLoadData)
    }



/*
*/

/*
*/

     /*   fun  searchRx(searchView: SearchView): Observable<String>{*/
     /*       val subject = PublishSubject.create<String>()*/
     /*       searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{*/
     /*           override fun onQueryTextSubmit(query: String?): Boolean {*/
     /*               return true*/
     /*           }*/
/*
*/

     /*           override fun onQueryTextChange(newText: String?): Boolean {*/
     /*               return true*/
     /*           }*/
/*
*/

     /*       })*/
     /*       return subject*/
     /*   }*/

}
