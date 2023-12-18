package com.example.realtimedatabase;

public class Model {
   private String employeeName;

   // string variable for storing
   // employee contact number
   private String employeeContactNumber;

   // string variable for storing
   // employee address.
   private String employeeAddress;

   private String imageUri;

   // an empty constructor is
   // required when using
   // Firebase Realtime Database.



   public Model( String employeeAddress,String employeeContactNumber,String employeeName,String imageUri) {
      this.employeeName = employeeName;
      this.employeeContactNumber = employeeContactNumber;
      this.employeeAddress = employeeAddress;
      this.imageUri=imageUri;
   }


   public Model(){

   }

   // created getter and setter methods
   // for all our variables.
   public String getEmployeeName() {
      return employeeName;
   }

   public void setEmployeeName(String employeeName) {
      this.employeeName = employeeName;
   }

   public String getEmployeeContactNumber() {
      return employeeContactNumber;
   }

   public void setEmployeeContactNumber(String employeeContactNumber) {
      this.employeeContactNumber = employeeContactNumber;
   }

   public String getEmployeeAddress() {
      return employeeAddress;
   }

   public String getImageUri() {
      return imageUri;
   }

   public void setImageUri(String imageUri) {
      this.imageUri = imageUri;
   }

   public void setEmployeeAddress(String employeeAddress) {
      this.employeeAddress = employeeAddress;
   }


}
