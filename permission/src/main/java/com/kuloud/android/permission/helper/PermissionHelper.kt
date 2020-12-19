package com.kuloud.android.permission.helper

import android.app.Activity
import android.content.Context
import androidx.annotation.StyleRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

/**
 * Delegate class to make permission calls based on the 'host' (Fragment, Activity, etc).
 */
abstract class PermissionHelper<out T>(val host: T) {
    private fun shouldShowRationale(vararg perms: String): Boolean {
        for (perm in perms) {
            if (shouldShowRequestPermissionRationale(perm)) {
                return true
            }
        }
        return false
    }

    fun requestPermissions(
        rationale: String,
        positiveButton: String,
        negativeButton: String,
        @StyleRes theme: Int,
        requestCode: Int,
        vararg perms: String
    ) {
        if (shouldShowRationale(*perms)) {
            showRequestPermissionRationale(
                rationale, positiveButton, negativeButton, theme, requestCode, *perms
            )
        } else {
            directRequestPermissions(requestCode, *perms)
        }
    }

    fun somePermissionPermanentlyDenied(perms: List<String>): Boolean {
        for (deniedPermission in perms) {
            if (permissionPermanentlyDenied(deniedPermission)) {
                return true
            }
        }
        return false
    }

    fun permissionPermanentlyDenied(perms: String): Boolean {
        return !shouldShowRequestPermissionRationale(perms)
    }

    fun somePermissionDenied(vararg perms: String): Boolean {
        return shouldShowRationale(*perms)
    }

    abstract fun directRequestPermissions(requestCode: Int, vararg perms: String)
    abstract fun shouldShowRequestPermissionRationale(perm: String): Boolean
    abstract fun showRequestPermissionRationale(
        rationale: String,
        positiveButton: String,
        negativeButton: String,
        @StyleRes theme: Int,
        requestCode: Int,
        vararg perms: String
    )

    abstract val context: Context?

    companion object {
        @JvmStatic
        fun newInstance(host: Activity): PermissionHelper<Activity> {
            return if (host is AppCompatActivity)
                AppCompatActivityPermissionsHelper(host)
            else {
                ActivityPermissionHelper(host)
            }
        }

        @JvmStatic
        fun newInstance(host: Fragment): PermissionHelper<Fragment> {
            return SupportFragmentPermissionHelper(host)
        }
    }
}