package com.utils

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.content.res.ColorStateList
import android.graphics.Color
import android.net.Uri
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.widget.ImageViewCompat
import com.amulyakhare.textdrawable.TextDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.util.*


fun String.loadCircleImage(context: Context, imageView: ImageView) {
    Glide.with(context)
        .load(this)
        .apply(RequestOptions.circleCropTransform())
        .into(imageView)
}

fun String.loadImage(context: Context, imageView: ImageView) {
    Glide.with(context)
        .load(this)
        .apply(RequestOptions.centerCropTransform())
        .into(imageView)
}

fun getRandomColor(): Int {
    val rnd = Random()
    return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
}

fun String.getTextDrawable(contactColor: Int? = null): TextDrawable {
    return TextDrawable.builder()
        .buildRound(this[0].toString(), contactColor ?: getRandomColor())
}

fun AppCompatImageView.setVectorTint(color: Int) {
    ImageViewCompat.setImageTintList(this, ColorStateList.valueOf(color))
}

fun Context.call(phoneNumber: String) {
    val intent = Intent(Intent.ACTION_DIAL).apply {
        data = Uri.parse("tel:$phoneNumber")
        flags = FLAG_ACTIVITY_NEW_TASK
    }
    startActivity(intent)
}

fun Context.sendSMS(phoneNumber: String) {
    try {
        val smsIntent = Intent(Intent.ACTION_VIEW).apply {
            flags = FLAG_ACTIVITY_NEW_TASK
            putExtra("address", phoneNumber)
            addCategory(Intent.CATEGORY_DEFAULT)
            type = "vnd.android-dir/mms-sms"
            data = Uri.parse("sms:$phoneNumber")
        }
        startActivity(smsIntent)
    } catch (ex: ActivityNotFoundException) {
        ex.printStackTrace()
    }
}


fun Context.sendEmail(emailId: String) {
    try {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, emailId)
            flags = FLAG_ACTIVITY_NEW_TASK
            addCategory(Intent.CATEGORY_DEFAULT)
        }
        startActivity(intent)
    } catch (ex: ActivityNotFoundException) {
        ex.printStackTrace()
    }
}


