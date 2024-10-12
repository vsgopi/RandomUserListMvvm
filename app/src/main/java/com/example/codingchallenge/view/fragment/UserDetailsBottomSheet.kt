package com.example.codingchallenge.view.fragment

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.codingchallenge.R
import com.example.codingchallenge.databinding.BottomUserDetailsSummaryBinding
import com.example.codingchallenge.model.data.User
import com.example.codingchallenge.utilities.DateFormatUtil
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class UserDetailsBottomSheet(private val user: User) : BottomSheetDialogFragment() {

    private var _binding: BottomUserDetailsSummaryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)

        dialog.setOnShowListener {
            val bottomSheet =
                (it as BottomSheetDialog).findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet?.let { sheet ->
                sheet.background =
                    ContextCompat.getDrawable(requireContext(), R.drawable.rounded_bottom_sheet)
            }
        }

        return dialog
    }

    override fun onStart() {
        super.onStart()
        // Set the bottom sheet height to 60% of the screen
        dialog?.let { dialog ->
            val bottomSheet =
                dialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet?.let {
                val behavior = BottomSheetBehavior.from(it)
                it.layoutParams.height = (resources.displayMetrics.heightPixels * 0.6).toInt()
                behavior.state =
                    BottomSheetBehavior.STATE_EXPANDED
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomUserDetailsSummaryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        binding.userName.text = getString(R.string.user_full_name, user.name.first, user.name.last)
        binding.userDob.text = DateFormatUtil.dobFormat(user.dob.date)
        binding.userEmail.text = user.email
        binding.userGender.text = user.gender
        binding.userPhone.text = user.phone
        binding.userAddress.text = getString(
            R.string.user_address,
            user.location.street.number,
            user.location.street.name,
            user.location.city,
            user.location.state
        )

        Glide.with(this)
            .load(user.picture.large)
            .placeholder(R.drawable.ic_image_placeholder)
            .centerCrop()
            .into(binding.userImage)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
