package com.example.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_bmi.*
import java.math.BigDecimal
import java.math.RoundingMode

class BmiActivity : AppCompatActivity() {

    val METRIC_UNITS_VIEW= "METRIC_UNIT_VIEW"
    val US_UNITS_VIEW= "US_UNIT_VIEW"
    var currentVisibleView:String= METRIC_UNITS_VIEW

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmi)

        setSupportActionBar(toolbar_bmi_activity)
        if(supportActionBar!=null){
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.title="Calculate Title"
        }
        toolbar_bmi_activity.setNavigationOnClickListener {
            onBackPressed()
        }
        btnCalculateUnits.setOnClickListener {
            if(currentVisibleView == METRIC_UNITS_VIEW){
                if(validateMetricUnits()){
                    val heightValue:Float=etMetricUnitHeight.text.toString().toFloat()/100
                    val weightValue:Float=etMetricUnitWeight.text.toString().toFloat()
                    val bmi= weightValue/(heightValue*heightValue)
                    displayBmiResult(bmi)
                }else{
                    Toast.makeText(this,"Please enter valid values",Toast.LENGTH_SHORT).show()
                }
            }else{
                if(validateUSUnits()){
                    val usUnitHeightValueFeet:String=etUsUnitHeightFeet.text.toString()
                    val usUnitHeightValueInch:String=etUsUnitHeightInch.text.toString()
                    val usUnitWeightValue:Float=etUsUnitWeight.text.toString().toFloat()

                    val heightValue=usUnitHeightValueInch.toFloat()+usUnitHeightValueFeet.toFloat()*12

                    val bmi= 703*(usUnitWeightValue/(heightValue*heightValue))
                    displayBmiResult(bmi)

                }else{
                    Toast.makeText(this,"Please enter valid values in US Units view",Toast.LENGTH_SHORT).show()
                }
            }
        }
        makeVisibleMetricUnitsView()//initial view will be metric view

        //changing Metric View and Us viw using radio button
        rgUnits.setOnCheckedChangeListener { group, checkedId ->
            if(checkedId== R.id.rbMetricUnits){
                makeVisibleMetricUnitsView()
            }else{
                makeVisibleUsUnitsView()
            }
        }
    }
    fun validateMetricUnits():Boolean{
        var isValid=true
        if(etMetricUnitHeight.text.toString().isEmpty()){
            isValid=false
        }else if (etMetricUnitWeight.text.toString().isEmpty()){
            isValid=false
        }
        return isValid
    }

    fun validateUSUnits():Boolean{
        var isValidd=true

        if(etUsUnitHeightFeet.text.toString().isEmpty()){
                isValidd=false
            }
        else if(etUsUnitWeight.toString().isEmpty())
             {
                isValidd=false
            }
        else if(etUsUnitHeightInch.text.toString().isEmpty() )
            {
                isValidd=false
            }
        return isValidd
        }



    fun makeVisibleMetricUnitsView(){
        currentVisibleView=METRIC_UNITS_VIEW
        tilMetricUnitWeight.visibility=View.VISIBLE
        tilMetricUnitHeight.visibility=View.VISIBLE

        etMetricUnitWeight.text!!.clear()
        etMetricUnitHeight.text!!.clear()

        tilUsUnitWeight.visibility=View.GONE
        llUsUnitsHeight.visibility=View.GONE

        llDisplayBMIResult.visibility=View.INVISIBLE
    }

    fun makeVisibleUsUnitsView(){
        currentVisibleView=US_UNITS_VIEW
        tilMetricUnitWeight.visibility=View.GONE
        tilMetricUnitHeight.visibility=View.GONE
        etUsUnitHeightFeet.text!!.clear()
        etUsUnitWeight.text!!.clear()
        etUsUnitHeightInch.text!!.clear()

        tilUsUnitWeight.visibility=View.VISIBLE
        llUsUnitsHeight.visibility=View.VISIBLE

        llDisplayBMIResult.visibility=View.INVISIBLE
    }

    fun displayBmiResult(bmi:Float){
        val bmiLabel:String
        val bmiDescription:String
        if (bmi.compareTo(15f) <= 0) {
            bmiLabel = "Very severely underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <= 0
        ) {
            bmiLabel = "Severely underweight"
            bmiDescription = "Oops!You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <= 0
        ) {
            bmiLabel = "Underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0
        ) {
            bmiLabel = "Normal"
            bmiDescription = "Congratulations! You are in a good shape!"
        } else if (java.lang.Float.compare(bmi, 25f) > 0 && java.lang.Float.compare(
                bmi,
                30f
            ) <= 0
        ) {
            bmiLabel = "Overweight"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <= 0
        ) {
            bmiLabel = "Obese Class | (Moderately obese)"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <= 0
        ) {
            bmiLabel = "Obese Class || (Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        } else {
            bmiLabel = "Obese Class ||| (Very Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        }
        llDisplayBMIResult.visibility=View.VISIBLE

        // This is used to round the result value to 2 decimal values after "."
        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()

        tvBMIValue.text = bmiValue // Value is set to TextView
        tvBMIType.text = bmiLabel // Label is set to TextView
        tvBMIDescription.text = bmiDescription // Description is set to TextView
    }
}