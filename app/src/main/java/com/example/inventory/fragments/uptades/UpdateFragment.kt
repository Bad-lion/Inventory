package com.example.inventory.fragments.uptades

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.util.Log.d
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.core.view.drawToBitmap
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.inventory.R
import com.example.inventory.model.Things
import com.example.inventory.vewmodel.ThingViewModel
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*


class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()

    private lateinit var mThingViewModel: ThingViewModel

    @SuppressLint("RestrictedApi")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_update, container, false)

        d("arst","before view model")
        mThingViewModel = ViewModelProvider(this).get(ThingViewModel::class.java)

        d("arst","after view model")
        view.updateTextThingTitle.setText(args.currentThing.title)
        view.updateTextQuantity.setText(args.currentThing.quantity.toString())
        view.updateTextPrice.setText(args.currentThing.price.toString())
        view.updateTextSupplier.setText(args.currentThing.supplier)

        view.infoTextThingTitle.text = args.currentThing.title
        view.infoTextQuantity.text= args.currentThing.quantity.toString()
        view.infoTextPrice.text = args.currentThing.price.toString()
        view.infoTextSupplier.text = args.currentThing.supplier

        view.update_button.setOnClickListener {
            try {
                updatItem()
            }catch (e: NumberFormatException){
                Toast.makeText(requireContext(),"please fill all the fields",Toast.LENGTH_SHORT).show()
            }

            view.infoTextPrice.visibility = View.VISIBLE
            view.infoTextQuantity.visibility = View.VISIBLE
            view.infoTextSupplier.visibility = View.VISIBLE
            view.infoTextThingTitle.visibility = View.VISIBLE
            view.infoImage.visibility = View.VISIBLE

            view.updateTextThingTitle.visibility = View.GONE
            view.updateTextQuantity.visibility = View.GONE
            view.updateTextPrice.visibility = View.GONE
            view.updateTextSupplier.visibility = View.GONE
            view.updateImage.visibility = View.GONE

            view.floatingActionButtonEdit.visibility = View.VISIBLE
            view.update_button.visibility = View.GONE

            d("arst","from button setOn clik")


        }
        setHasOptionsMenu(true)



        view.floatingActionButtonEdit.setOnClickListener {
            view.infoTextPrice.visibility = View.GONE
            view.infoTextQuantity.visibility = View.GONE
            view.infoTextSupplier.visibility = View.GONE
            view.infoTextThingTitle.visibility = View.GONE

            view.updateTextThingTitle.visibility = View.VISIBLE
            view.updateTextQuantity.visibility = View.VISIBLE
            view.updateTextPrice.visibility = View.VISIBLE
            view.updateTextSupplier.visibility = View.VISIBLE

            view.update_button.visibility = View.VISIBLE
            view.floatingActionButtonEdit.visibility = View.GONE

            view.update_button.visibility = View.VISIBLE
            view.floatingActionButtonEdit.visibility = View.GONE



        }

        return view
    }

    private fun updatItem(){
        val title = updateTextThingTitle.text.toString()
        val price = Integer.parseInt(updateTextPrice.text.toString())
        val quantity = Integer.parseInt(updateTextQuantity.text.toString())
        val supplier = updateTextSupplier.text.toString()

        if (inputCheck(title,updateTextPrice.text,updateTextQuantity.text,supplier)){
            val updated = Things(args.currentThing.id,title,price,quantity,supplier)

            mThingViewModel.updateThing(updated)
            Toast.makeText(requireContext(),"updated successfully",Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }else{
            Toast.makeText(requireContext(),"please fill all the fields",Toast.LENGTH_SHORT).show()
        }

    }

    private fun inputCheck (
        title: String,
        price: Editable,
        quantity: Editable,
        supplier: String
    ): Boolean{
        return !(TextUtils.isEmpty(title) || TextUtils.isEmpty(supplier) || price.isEmpty() || quantity.isEmpty())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.delet_menu){
            deleteTHing()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteTHing() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes"){_,_->
            mThingViewModel.deleteThing(args.currentThing)
            Toast.makeText(requireContext(),"Successfully remove",Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton("No"){_,_->}
        builder.setTitle("Delete ${args.currentThing.title}?")
        builder.setMessage("Are you sure want to delete ${args.currentThing.title}?")
        builder.create().show()
    }


}