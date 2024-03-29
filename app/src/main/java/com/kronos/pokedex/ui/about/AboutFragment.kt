package com.kronos.pokedex.ui.about

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kronos.pokedex.R
import dagger.hilt.android.AndroidEntryPoint
import mehdi.sakout.aboutpage.AboutPage
import mehdi.sakout.aboutpage.Element
import java.util.*


@AndroidEntryPoint
class AboutFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return AboutPage(requireContext())
            .isRTL(false)
            .setImage(R.drawable.ic_pokedex_app_icon)
            .setDescription(getString(R.string.app_description))
            .addGroup(getString(R.string.programmer_group))
            .addEmail("mguerral1993@gmail.com", "Email")
            .addGitHub("Kronos1993","GitHub")
            .addGroup(getString(R.string.app_data_group))
            .addItem(Element(getAppVersion(), R.drawable.ic_pokemon_info))
            .addItem(Element(getCopyRight(), R.drawable.ic_copyright))
            .create()
    }

    private fun getCopyRight(): String {
        return String.format(getString(R.string.copy_right), 2023);
    }


    private fun getAppVersion(): String {
        try {
            val packageInfo = context?.packageManager?.getPackageInfo("com.kronos.pokedex", 0)
            return packageInfo?.versionName.toString()
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return ""
    }


}