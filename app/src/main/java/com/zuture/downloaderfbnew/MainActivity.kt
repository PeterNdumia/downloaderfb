package com.zuture.downloaderfbnew

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import com.google.android.material.tabs.TabLayout
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.facebook.ads.*
import com.zuture.downloaderfbnew.utils.IOUtils
import com.zuture.downloaderfbnew.utils.iUtils
import com.suddenh4x.ratingdialog.AppRating
import com.suddenh4x.ratingdialog.preferences.RatingThreshold
import java.util.*

class MainActivity : AppCompatActivity() {


    val REQUEST_PERMISSION_CODE = 1001
    val REQUEST_PERMISSION = android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    private var tabLayout: TabLayout? = null
    var viewPager: ViewPager? = null
    private var nativeAd: NativeAd? = null
    private var nativeAdtwo: NativeAd? = null
    private var nativeAdthree: NativeAd? = null

    private val TAG: String = MainActivity::class.java.getSimpleName()

    private var interstitialAd: InterstitialAd? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(!isNeedGrantPermission()) {
            setlayout()
        }



        AudienceNetworkAds.initialize(this)


        nativeAd = NativeAd(this, "1477256725945757_1477263712611725")


        val nativeAdListener: NativeAdListener = object  : NativeAdListener {
            override fun onError(p0: Ad?, p1: AdError?) {

                Log.e(TAG, "Native ad failed to load: ")
            }

            override fun onAdLoaded(p0: Ad?) {
                val adView: View = NativeAdView.render(this@MainActivity, nativeAd)
                val nativeAdContainer: LinearLayout =
                    findViewById<View>(R.id.my_template) as LinearLayout
                nativeAdContainer.addView(
                    adView, LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                )
            }

            override fun onAdClicked(p0: Ad?) {
                Log.d(TAG, "Native ad clicked!");
            }

            override fun onLoggingImpression(p0: Ad?) {
                Log.d(TAG, "Native ad impression logged!")
            }

            override fun onMediaDownloaded(p0: Ad?) {

                Log.e(TAG, "Native ad finished downloading all assets.");
            }

        }
        nativeAd!!.loadAd(
            nativeAd!!.buildLoadAdConfig()
                .withAdListener(nativeAdListener)
                .withMediaCacheFlag(NativeAdBase.MediaCacheFlag.ALL)
                .build()
        )


        nativeAdtwo = NativeAd(
            this,
            "1477256725945757_1477263712611725"
        )


        val nativeAdListenertwo: NativeAdListener = object  : NativeAdListener {
            override fun onError(p0: Ad?, p1: AdError?) {

                Log.e(TAG, "Native ad failed to load: ")
            }

            override fun onAdLoaded(p0: Ad?) {
                val adViewtwo: View = NativeAdView.render(this@MainActivity, nativeAdtwo)
                val nativeAdContainer: LinearLayout =
                    findViewById<View>(R.id.my_secondtemplate) as LinearLayout
                nativeAdContainer.addView(
                    adViewtwo, LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                )
            }

            override fun onAdClicked(p0: Ad?) {
                Log.d(TAG, "Native ad clicked!");
            }

            override fun onLoggingImpression(p0: Ad?) {
                Log.d(TAG, "Native ad impression logged!")
            }

            override fun onMediaDownloaded(p0: Ad?) {

                Log.e(TAG, "Native ad finished downloading all assets.");
            }

        }
        nativeAdtwo!!.loadAd(
            nativeAdtwo!!.buildLoadAdConfig()
                .withAdListener(nativeAdListenertwo)
                .withMediaCacheFlag(NativeAdBase.MediaCacheFlag.ALL)
                .build()
        )

        nativeAdthree = NativeAd(
            this,
            "1477256725945757_1477263712611725"
        )


        val nativeAdListenerthree: NativeAdListener = object  : NativeAdListener {
            override fun onError(p0: Ad?, p1: AdError?) {

                Log.e(TAG, "Native ad failed to load: ")
            }

            override fun onAdLoaded(p0: Ad?) {
                val adViewthree: View = NativeAdView.render(this@MainActivity, nativeAdthree)
                val nativeAdContainer: LinearLayout =
                    findViewById<View>(R.id.videotemplate) as LinearLayout
                nativeAdContainer.addView(
                    adViewthree, LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                )
            }

            override fun onAdClicked(p0: Ad?) {
                Log.d(TAG, "Native ad clicked!");
            }

            override fun onLoggingImpression(p0: Ad?) {
                Log.d(TAG, "Native ad impression logged!")
            }

            override fun onMediaDownloaded(p0: Ad?) {

                Log.e(TAG, "Native ad finished downloading all assets.");
            }

        }
        nativeAdthree!!.loadAd(
            nativeAdthree!!.buildLoadAdConfig()
                .withAdListener(nativeAdListenerthree)
                .withMediaCacheFlag(NativeAdBase.MediaCacheFlag.ALL)
                .build()
        )


        AppRating.Builder(this)
            .setMinimumLaunchTimes(2)
            .setMinimumDays(1)
            .setMinimumLaunchTimesToShowAgain(4)
            .setMinimumDaysToShowAgain(2)
            .setRatingThreshold(RatingThreshold.FOUR)
            .showIfMeetsConditions()


        viewPager?.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                if (position==1)
                    interstitialAd = InterstitialAd(
                        this@MainActivity,
                        "1477256725945757_1477263415945088"
                    )
                // Create listeners for the Interstitial Ad
                val interstitialAdListener: InterstitialAdListener =
                    object : InterstitialAdListener {
                        override fun onInterstitialDisplayed(ad: Ad) {
                            // Interstitial ad displayed callback
                            Log.e(TAG, "Interstitial ad displayed.")
                        }

                        override fun onInterstitialDismissed(ad: Ad) {
                            // Interstitial dismissed callback
                            Log.e(TAG, "Interstitial ad dismissed.")
                        }

                        override fun onError(ad: Ad, adError: AdError) {
                            // Ad error callback
                            Log.e(TAG, "Interstitial ad failed to load: " + adError.errorMessage)
                        }

                        override fun onAdLoaded(ad: Ad) {
                            // Interstitial ad is loaded and ready to be displayed
                            Log.d(TAG, "Interstitial ad is loaded and ready to be displayed!")
                            // Show the ad
                            interstitialAd!!.show()
                        }

                        override fun onAdClicked(ad: Ad) {
                            // Ad clicked callback
                            Log.d(TAG, "Interstitial ad clicked!")
                        }

                        override fun onLoggingImpression(ad: Ad) {
                            // Ad impression logged callback
                            Log.d(TAG, "Interstitial ad impression logged!")
                        }
                    }

                // For auto play video ads, it's recommended to load the ad
                // at least 30 seconds before it is shown
                interstitialAd!!.loadAd(
                    interstitialAd!!.buildLoadAdConfig()
                        .withAdListener(interstitialAdListener)
                        .build()
                )
            }

        })

    }



    fun setlayout(){
        viewPager = findViewById<ViewPager>(R.id.viewpager)
        setupViewPager(viewPager!!)

        tabLayout = findViewById<TabLayout>(R.id.tabs)
        tabLayout!!.setupWithViewPager(viewPager)
        setupTabIcons()
    }

    fun setupTabIcons(){
        tabLayout?.getTabAt(0)?.setIcon(R.drawable.ic_download_color_24dp)
        tabLayout?.getTabAt(1)?.setIcon(R.drawable.ic_gallery_color_24dp)

    }
    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(download(), "Download")
        adapter.addFragment(gallery(), "Gallery")
        viewPager.adapter = adapter


    }


    private fun isNeedGrantPermission(): Boolean {
        try {
            if (IOUtils.hasMarsallow()) {
                if (ContextCompat.checkSelfPermission(this, REQUEST_PERMISSION) != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this@MainActivity, REQUEST_PERMISSION)) {
                        val msg =
                            String.format(getString(R.string.format_request_permision), getString(R.string.app_name))

                        val localBuilder = AlertDialog.Builder(this@MainActivity)
                        localBuilder.setTitle("Permission Required!")
                        localBuilder
                            .setMessage(msg).setNeutralButton(
                                "Grant"
                            ) { paramAnonymousDialogInterface, paramAnonymousInt ->
                                ActivityCompat.requestPermissions(
                                    this@MainActivity,
                                    arrayOf(REQUEST_PERMISSION),
                                    REQUEST_PERMISSION_CODE
                                )
                            }
                            .setNegativeButton(
                                "Cancel"
                            ) { paramAnonymousDialogInterface, paramAnonymousInt ->
                                paramAnonymousDialogInterface.dismiss()
                                finish()
                            }
                        localBuilder.show()

                    } else {
                        ActivityCompat.requestPermissions(this, arrayOf(REQUEST_PERMISSION), REQUEST_PERMISSION_CODE)
                    }
                    return true
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return false

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        try {
            if (requestCode == REQUEST_PERMISSION_CODE) {
                if (grantResults != null && grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //
                    setlayout()
                } else {
                    iUtils.ShowToast(this@MainActivity, getString(R.string.info_permission_denied))

                    finish()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            iUtils.ShowToast(this@MainActivity, getString(R.string.info_permission_denied))
            finish()
        }

    }
    internal inner class ViewPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {
        private val mFragmentList = ArrayList<Fragment>()
        private val mFragmentTitleList = ArrayList<String>()

        override fun getItem(position: Int): Fragment {

            return mFragmentList[position]

//            viewPager!!.currentItem;
//            return when(position){
//
//                0-> download();
//                1->gallery();
//                else -> gallery();
//            }
        }

        override fun getCount(): Int {
            return mFragmentList.size
        }

        fun addFragment(fragment: Fragment, title: String) {
            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence {
            return mFragmentTitleList[position]
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_rate -> {

                androidx.appcompat.app.AlertDialog.Builder(this)
                    .setTitle(getString(R.string.RateAppTitle))
                    .setMessage(getString(R.string.RateApp))
                    .setCancelable(false)
                    .setPositiveButton("RATE", DialogInterface.OnClickListener { dialog, whichButton ->
                        val appPackageName = packageName
                        try {
                            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$appPackageName")))
                        } catch (anfe: android.content.ActivityNotFoundException) {
                            startActivity(
                                Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
                                )
                            )
                        }
                    })
                    .setNegativeButton("LATER", null).show()

                true
            }


            else -> super.onOptionsItemSelected(item)
        }
    }
}