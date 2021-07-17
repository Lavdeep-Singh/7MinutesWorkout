package com.example.a7minutesworkout
/**
 * This is used for exercise details it is a custom model class.
 * with getter setter functions and a constructor
 */
class ExerciseModel(
    var id:Int,
    private var name: String,
    private var image: Int,
    private var isCompleted: Boolean,
    private var isSelected: Boolean
) {
    //these are getters and setters used to get and set values easily
    @JvmName("getId1")
    fun getId(): Int {
        return id
    }

    @JvmName("setId1")
    fun setId(id: Int) {
        this.id = id
    }

    fun getName(): String {
        return name
    }

    fun setName(name: String) {
        this.name = name
    }

    fun getImage(): Int {
        return image
    }

    fun setImage(image: Int) {
        this.image = image
    }

    fun getIsCompleted(): Boolean {
        return isCompleted
    }

    fun setIsCompleted(isCompleted: Boolean) {
        this.isCompleted = isCompleted
    }

    fun getIsSelected(): Boolean {
        return isSelected
    }

    fun setIsSelected(isSelected: Boolean) {
        this.isSelected = isSelected
    }

}