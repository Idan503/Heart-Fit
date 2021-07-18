package com.idan_koren_israeli.heartfit.mvvm.view.dialog

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.idan_koren_israeli.heartfit.R
import com.idan_koren_israeli.heartfit.db.firebase.database.DatabaseManager
import com.idan_koren_israeli.heartfit.db.firebase.firestore.FirestoreManager
import com.idan_koren_israeli.heartfit.mvvm.repository.Equipment
import com.idan_koren_israeli.heartfit.mvvm.model.Workout

class WorkoutStartDialogManager(val activity: Activity) {
    private lateinit var materialAlertDialogBuilder: MaterialAlertDialogBuilder
    private lateinit var scrollLayout : ViewGroup
    private lateinit var dialogLayout : ViewGroup

    fun create(){
        materialAlertDialogBuilder = MaterialAlertDialogBuilder(activity)
    }


    fun inflate(){
        dialogLayout = (LayoutInflater.from(activity)
            .inflate(R.layout.dialog_workout_start, null, false) as ViewGroup)

        scrollLayout = dialogLayout.findViewById(R.id.workout_start_dialog_SCRL_parent)
    }

    private fun addEquipmentToLayout(allEquipment: List<Equipment>){

        for(equipment in allEquipment){
            val equipmentHolder = LayoutInflater.from(activity)
                .inflate(R.layout.holder_equipment_select, scrollLayout, false)


            equipmentHolder.layoutParams =
                ViewGroup.LayoutParams(160, ViewGroup.LayoutParams.WRAP_CONTENT)



            equipmentHolder.findViewById<TextView>(R.id.equipment_card_LBL_label).text = equipment.displayName
            val equipmentImage = equipmentHolder.findViewById<ImageView>(R.id.equipment_card_IMG_image)
            equipmentImage.setImageDrawable(ContextCompat.getDrawable(activity,equipment.imageId))

            scrollLayout.addView(equipmentHolder)
        }




    }

    fun launch(workout: Workout) {
        // Building the Alert dialog using materialAlertDialogBuilder instance
        addEquipmentToLayout(workout.equipment)


        materialAlertDialogBuilder.setView(dialogLayout)
            .setTitle("Start " + workout.name)
            .setMessage("Get " + workout.heartsValue + " hearts by finishing this workout!\n\n" +
                    "Equipment you will need:")
            .setPositiveButton("Start") { dialog, _ ->


                FirestoreManager.loadExercisesByName("Bench press") {
                    val bundle = bundleOf(
                        "workout" to workout,
                        "exercises" to it,
                        "user" to DatabaseManager.currentUser
                    )

                    activity.findNavController(R.id.mainActivity_fragment)
                        .navigate(R.id.action_fragmentHome_to_fragmentWorkout, bundle)
                }


                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    fun findViews(){

    }
}