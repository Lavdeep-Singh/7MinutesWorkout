package com.example.a7minutesworkout

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.provider.MediaStore
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_exercise.*
import kotlinx.android.synthetic.main.dialog_custom_back_confirmation.*
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

//adding text to speech functionality
class ExerciseActivity : AppCompatActivity(),TextToSpeech.OnInitListener  {

    var restTimer:CountDownTimer?=null
    var restProgress=0 //progress done
    var pauseOffset:Long=0

    var exerciseTimer:CountDownTimer?=null
    var exerciseProgress=0

    var exerciseList:ArrayList<ExerciseModel>?=null
    var currentExercisePosition=-1

    var tts:TextToSpeech?=null //var to initialize textToSpeech Interface
    var player:MediaPlayer?=null//media player variable

    var exerciseAdapter:ExerciseStatusAdapter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)

        setSupportActionBar(toolbar_exercise_activity) //setting toolbar as action bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)//if supportActionBar!=null make the icon clickable and add the < at the left of the icon
        //actionBar.setDisplayHomeAsUpEnabled(true) will make the icon clickable and add the < at the left of the icon.
        //actionBar.setHomeButtonEnabled(true) will just make the icon clickable, with the color at the background of the icon as a feedback of the click.
        //setting clickListener on toolbar and adding functionality
        toolbar_exercise_activity.setNavigationOnClickListener {
            customDialogForBackButton()
        }

        tts= TextToSpeech(this,this)//speaking and listening activity, initializing
        exerciseList=Constants.defaultExerciseList()//returns list of exercises with data
        setupRestView() //calling function

        setupExerciseStatusRecyclerView()//calling adapter function
    }

    override fun onDestroy() {
        if(restTimer!=null){
            restTimer!!.cancel()
            restProgress=0
        }
        if(exerciseTimer!=null){
            exerciseTimer!!.cancel()
            exerciseProgress=0
        }
        if(tts!=null){
            tts!!.stop()
            tts!!.shutdown()
        }
        if(player!=null){
            player!!.start()
        }
        super.onDestroy()
    }

    fun setRestProgressBar(){
        progressBar.progress=restProgress //setting progress of progress bar
        restTimer=object :CountDownTimer(
            10000,1000
        ){
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                progressBar.progress=(10-restProgress)//decreasing progress of progress bar
                tvTimer.text= (10-restProgress).toString()//setting remaining progress/time
            }

            override fun onFinish() {
                currentExercisePosition++
                exerciseList!![currentExercisePosition].setIsSelected(true)
                exerciseAdapter!!.notifyDataSetChanged()
                setupExerciseView()
            }

        }.start()
    }
    fun setupRestView(){
        try{
            player=MediaPlayer.create(applicationContext,R.raw.pristine)//setting sound to be played
            player!!.isLooping=false
            player!!.start()
        }catch (e:Exception){
            e.printStackTrace()
        }
        llRestView.visibility=View.VISIBLE
        llExerciseView.visibility=View.GONE
        if(restTimer!=null){//if timer already running, stop it
           restTimer!!.cancel()
            restProgress=0
        }
        setRestProgressBar()//calling fun
        upcomingExerciseName.text=exerciseList!![currentExercisePosition+1].getName()
    }
    //counter for exercise
    fun setExerciseProgressBar(){
        exerciseProgressBar.progress=exerciseProgress //setting progress of progress bar
        exerciseTimer=object :CountDownTimer(
            30000-pauseOffset,1000
        ){
            override fun onTick(millisUntilFinished: Long) {
                this@ExerciseActivity.pauseOffset =30000-millisUntilFinished
                exerciseProgress++
                exerciseProgressBar.progress=(30-exerciseProgress)//decreasing progress of progress bar
                tvTimerExercise.text= (30-exerciseProgress).toString()//setting remaining progress/time
            }

            override fun onFinish() {
                pauseOffset=0
                exerciseList!![currentExercisePosition].setIsSelected(false)
                exerciseList!![currentExercisePosition].setIsCompleted(true)
                exerciseAdapter!!.notifyDataSetChanged()
                if(currentExercisePosition<11){
                    setupRestView()
                }else{
                    finish()//finishing current activity
                    startActivity(Intent(this@ExerciseActivity,FinishActivity::class.java))//starting finish activity

                }
            }

        }.start()
    }
    fun pauseTimer(){
        if(exerciseTimer!=null){
            exerciseTimer!!.cancel()
        }
    }

    fun setupExerciseView(){
        llRestView.visibility=View.GONE
        llExerciseView.visibility= View.VISIBLE
        if(exerciseTimer!=null){//if timer already running, stop it
            exerciseTimer!!.cancel()
            exerciseProgress=0
        }
        //setting name and image to the view
        ivImage.setImageResource(exerciseList!![currentExercisePosition].getImage())
        tvExerciseName.text=exerciseList!![currentExercisePosition].getName()

        speakOut(exerciseList!![currentExercisePosition].getName())//speak exercise name
        setExerciseProgressBar()//calling fun

    }

    override fun onInit(status: Int) { // checking text to speech compatibility with the device
        if(status==TextToSpeech.SUCCESS){ //text to speech available in the device
            val result= tts!!.setLanguage(Locale.US) //setting language to us english
            if(result==TextToSpeech.LANG_MISSING_DATA || result==TextToSpeech.LANG_NOT_SUPPORTED){ //checking language is supported or not
                 Log.e("TTS","language specified is not supported !!!!")
            }
        }else{//tts not suppoted
            Log.e("TTS","TTS not supported")
        }

    }
    fun speakOut(text:String){ //calls speak method
        tts!!.speak(text,TextToSpeech.QUEUE_FLUSH,null,"")
    }

    fun setupExerciseStatusRecyclerView(){
        rvExerciseStatus.layoutManager=LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        exerciseAdapter= ExerciseStatusAdapter(exerciseList!!,this)
        rvExerciseStatus.adapter=exerciseAdapter

    }

    fun customDialogForBackButton(){
        val dialog= Dialog(this) //initialising
        dialog.setContentView(R.layout.dialog_custom_back_confirmation) //setting custom layout
        pauseTimer()
        dialog.tvYes.setOnClickListener {
            finish()
            dialog.dismiss()
        }
        dialog.tvNo.setOnClickListener {
            setExerciseProgressBar()
            exerciseProgress--
            dialog.dismiss()
        }
        dialog.show()
    }




}
