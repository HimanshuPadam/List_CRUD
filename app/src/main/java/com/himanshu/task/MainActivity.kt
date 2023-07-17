package com.himanshu.task

import android.app.AlertDialog
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.himanshu.task.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var dialog:Dialog
    var arrayList = arrayListOf<String>()
    lateinit var binding : ActivityMainBinding
    lateinit var adapter : ArrayAdapter<String>
    var tvValue :TextView ?= null
    var etValue: EditText ?= null
    var btnAdd: Button ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter= ArrayAdapter(this,android.R.layout.simple_list_item_1,arrayList)
        binding.listView.adapter =adapter

        //insertion in list using FAB button started
        binding.fab.setOnClickListener {
            dialog= Dialog(this)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.custom_dialog_layout)
            tvValue=dialog.findViewById<TextView>(R.id.tvValue)
            etValue=dialog.findViewById<EditText>(R.id.etValue)
            btnAdd=dialog.findViewById<Button>(R.id.btnAdd)
            btnAdd.setOnClickListener {
                if (etValue.text.toString().isNullOrEmpty()) {
                    etValue.error="Please Enter the value"
                }
                else {
                    arrayList.add(etValue.text.toString())
                    dialog.dismiss()
                }
            }
            dialog.show()
            adapter.notifyDataSetChanged()
        }
        //insertion in list using FAB button ended

        //AlertDialog box for update, delete or cancel on item click started
        binding.listView.setOnItemClickListener { parent, view, position, id ->
            var alert= AlertDialog.Builder(this)
            alert.setTitle("Update or Delete")
            alert.setMessage("Do you want to update or delete value?")
            alert.setCancelable(false)

            // updation started
            alert.setPositiveButton("Update") { _, _ ->
                dialog= Dialog(this)
                dialog.setCancelable(false)
                dialog.setContentView(R.layout.custom_dialog_layout)
                tvValue=dialog.findViewById<TextView>(R.id.tvValue)
                etValue=dialog.findViewById<EditText>(R.id.etValue)
                btnAdd=dialog.findViewById<Button>(R.id.btnAdd)
                tvValue.setText("Update item ${arrayList[position]}")
                etValue.setText("${arrayList[position]}")
                //etValue.setHint("${arrayList[position]}")
                btnAdd.setText("Update")
                btnAdd.setOnClickListener {
                    if (etValue.text.toString().isNullOrEmpty()) {
                        etValue.error="Please Enter the value"
                    }
                    else {
                        arrayList.set(position,etValue.text.toString())
                        dialog.dismiss()
                        Toast.makeText(this, "Updated successfully", Toast.LENGTH_SHORT).show()
                    }
                    adapter.notifyDataSetChanged()
                }
                dialog.show()
            }
            //updation ended

            //deletion started
            alert.setNegativeButton("Delete"){ _, _ ->
                arrayList.removeAt(position)
                Toast.makeText(this, "Deleted successfully", Toast.LENGTH_SHORT).show()
                adapter.notifyDataSetChanged()
            }
            //deletion ended

            alert.setNeutralButton("Cancel") { _, _ ->
                Toast.makeText(this,"Cancelled", Toast.LENGTH_SHORT).show()
            }
            alert.show()
            adapter.notifyDataSetChanged()
        }
        //AlertDialog box for update, delete or cancel on item click ended
    }

}
