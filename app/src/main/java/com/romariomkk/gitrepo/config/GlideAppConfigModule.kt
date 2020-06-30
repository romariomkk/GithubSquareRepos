package com.romariomkk.gitrepo.config

import android.content.Context
import android.util.Log
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.executor.GlideExecutor
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions

@com.bumptech.glide.annotation.GlideModule
class GlideAppConfigModule : AppGlideModule() {

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        val exceptionHandlingStrategy = GlideExecutor.UncaughtThrowableStrategy { t ->
            Log.e(GlideAppConfigModule::class.java.simpleName, t.message)
        }
        builder.setLogLevel(Log.VERBOSE)
                .setDiskCacheExecutor(GlideExecutor.newDiskCacheExecutor(exceptionHandlingStrategy))
                .setDefaultRequestOptions(
                        RequestOptions()
                                .format(DecodeFormat.DEFAULT)
                                .disallowHardwareConfig()
                                .diskCacheStrategy(DiskCacheStrategy.DATA))
    }

}