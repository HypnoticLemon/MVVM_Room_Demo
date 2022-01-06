package com.vikrant.simplemvvmroomdemo.Common

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vikrant.simplemvvmroomdemo.R
import com.vikrant.simplemvvmroomdemo.Utils.CustomProgressBar
import com.vikrant.simplemvvmroomdemo.databinding.FragmentBaseBinding
import java.lang.reflect.ParameterizedType
import java.text.SimpleDateFormat
import java.util.*


/**
 * Base fragment to standardize and simplify initialization for this component.
 *
 * @see Fragment
 */

abstract class BaseFragment<B : ViewDataBinding, V : ViewModel> : Fragment() {

    private var dataBinding: B? = null
    lateinit var viewModel: V

    companion object {
        val progressBar = CustomProgressBar()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = DataBindingUtil.inflate<FragmentBaseBinding>(
            inflater,
            R.layout.fragment_base,
            container,
            false
        )
        dataBinding = DataBindingUtil.inflate(inflater, onLayoutInflate(), view.rootView, true)
        dataBinding?.root?.let { DataBindingUtil.bind<B>(it) }
        viewModel = ViewModelProvider(this).get(getViewModelClass())
        return view.root
    }

    /**
     * Handles passing of layout resource file to the [BaseFragment] from
     * the calling fragment for layout inflation.
     */
    abstract fun onLayoutInflate(): Int

    /**
     * Gets the required [ViewModel] class for the calling activity
     */
    @Suppress("UNCHECKED_CAST")
    private fun getViewModelClass(): Class<V> {
        // index of 1 means second argument of BaseActivity class param
        val type = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[1]
        return type as Class<V>
    }

    /**
     * Shows toast of [Toast.LENGTH_SHORT] length with the given [message].
     */
    fun showToast(message: String) =
        context?.let { Toast.makeText(it, message, Toast.LENGTH_SHORT).show() }

    @SuppressLint("SimpleDateFormat")
    fun parseIntoFormattedDate(observationTime: String): Int {
        return try {
            val sdf =
                SimpleDateFormat("dd-MM-yyyy") //04-02-2020
            val cal = Calendar.getInstance()
            sdf.timeZone = TimeZone.getTimeZone(cal.timeZone.displayName)
            val formattedDate: Date = sdf.parse(observationTime)
            cal.time = formattedDate
            val date = cal.get(Calendar.DATE)
            date
        } catch (e: Exception) {
            e.printStackTrace()
            0
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun parseIntoFormattedMonth(observationTime: String): Int {
        return try {
            val sdf =
                SimpleDateFormat("dd-MM-yyyy") //04-02-2020
            val cal = Calendar.getInstance()
            sdf.timeZone = TimeZone.getTimeZone(cal.timeZone.displayName)
            val formattedDate: Date = sdf.parse(observationTime)
            cal.time = formattedDate
            var month = cal.get(Calendar.MONTH)
            month
        } catch (e: Exception) {
            e.printStackTrace()
            0
        }
    }


    @SuppressLint("SimpleDateFormat")
    fun parseIntoFormattedYear(observationTime: String): Int {
        return try {
            val sdf =
                SimpleDateFormat("dd-MM-yyyy") //04-02-2020
            val cal = Calendar.getInstance()
            sdf.timeZone = TimeZone.getTimeZone(cal.timeZone.displayName)
            val formattedDate: Date = sdf.parse(observationTime)
            cal.time = formattedDate
            var year = cal.get(Calendar.YEAR)
            year
        } catch (e: Exception) {
            e.printStackTrace()
            0
        }
    }
}