package co.kr.daou.autodistancedriven.feature

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import co.kr.daou.autodistancedriven.R
import co.kr.daou.autodistancedriven.feature.driving.fragment.DrivingFragment
import co.kr.daou.autodistancedriven.feature.driving.fragment.DrivingListDetailFragment
import co.kr.daou.autodistancedriven.feature.driving.fragment.DrivingListFragment
import co.kr.daou.autodistancedriven.util.FragmentName


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_layout_main_layout, DrivingFragment())
            .commit()
    }

    fun openFragment(fragmentName : FragmentName){
        val transaction = supportFragmentManager.beginTransaction()
        when(fragmentName){
            FragmentName.Home -> {
                val intent = Intent(this, MainActivity::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
            }
            FragmentName.Driving -> {
                transaction.replace(R.id.frame_layout_main_layout, DrivingFragment())
                    .addToBackStack(null)
            }
            FragmentName.DrivingList -> {
                transaction.replace(R.id.frame_layout_main_layout, DrivingListFragment())
                    .addToBackStack(null)
            }
        }
        transaction.commit()
    }
    fun openDrivingDetailFragment(recordId : Int){
        val bundle = Bundle()
        bundle.putInt("RECORDID",recordId)
        val drivingListDetailFragment = DrivingListDetailFragment()
        drivingListDetailFragment.arguments = bundle
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_layout_main_layout, drivingListDetailFragment)
            .addToBackStack(null)
            .commit()
    }
}