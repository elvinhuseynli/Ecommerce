package com.example.e_commerce.ui.fragments.main.home

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.e_commerce.R
import com.example.e_commerce.core_utils.listOfItems
import com.example.e_commerce.databinding.FragmentHomeBinding
import com.example.e_commerce.ui.adapters.CampaignsAdapter
import com.example.e_commerce.ui.adapters.CampaignsModel
import com.example.e_commerce.ui.adapters.StoriesAdapter
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import omari.hamza.storyview.StoryView
import omari.hamza.storyview.model.MyStory

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            val layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
            recyclerView.layoutManager = layoutManager
            recyclerView.adapter = StoriesAdapter(listOfItems) {
                buildAndShowStories(it)
            }
            recyclerView.visibility = View.VISIBLE
        }

        setListeners()

        setCampaignRV()

        generateBarcode()
    }

    private fun setListeners() = with(binding){
        seeAllBtn.setOnClickListener {
            findNavController().navigate(R.id.campaigns_nav_graph)
        }
    }

    private fun generateBarcode() = with(binding) {
        val userId = requireActivity().intent.extras?.getString("userId") ?: ""

        val mfw = MultiFormatWriter()

        mainLayout.post {
            val img = barcodeLayout.barcode

            try {
                val matrix = mfw.encode(userId, BarcodeFormat.CODE_128, img.width, img.height)

                val bitmap = Bitmap.createBitmap(img.width, img.height, Bitmap.Config.RGB_565)

                for (i in 0 until img.width) {
                    for (j in 0 until img.height) {
                        with(requireContext()) {
                            bitmap.setPixel(
                                i,
                                j,
                                if (matrix[i, j]) getColor(R.color.blue) else getColor(R.color.dark_white)
                            )
                        }
                    }
                }
                barcodeLayout.barcode.setImageBitmap(bitmap)
                llView.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
            } catch (e: Exception) {
                println(e)
            }
        }
    }

    private fun buildAndShowStories(position: Int) {

        val currentStory = ArrayList<MyStory>()
        currentStory.add(MyStory(listOfItems[position].link))

        StoryView.Builder(childFragmentManager)
            .setStoriesList(currentStory)
            .setStoryDuration(5000)
            .build()
            .show()
    }

    private fun setCampaignRV() = with(binding) {
        val layoutManager2 =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        rvCampaigns.layoutManager = layoutManager2

        val listOfCampaigns = arrayListOf<CampaignsModel>()

        Firebase.firestore.collection("campaigns")
            .get().addOnCompleteListener {
                if (it.isSuccessful) {
                    if (!it.result.isEmpty) {
                        for (doc in it.result) {
                            listOfCampaigns.add(
                                CampaignsModel(
                                    doc.getString("imgPath").orEmpty(),
                                    doc.getString("title").orEmpty(),
                                    doc.getString("dateRange").orEmpty()
                                )
                            )
                        }
                        rvCampaigns.adapter = CampaignsAdapter(listOfCampaigns) {

                        }
                        rvCampaigns.visibility = View.VISIBLE
                    }
                }
            }
    }
}