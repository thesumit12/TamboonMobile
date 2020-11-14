# TamboonMobile

## Business Requirement

  * Fetch list of Charity item from Server
  * Make donation to Charity
  
## Project Specification

  Project is developed using MVVM clean architecture in Kotlin language. App is getting list of Charity from server and populating on UI. 
  For donation Omise mobile SDK is used for CreditCard detail screen. For all network calls Coroutine has been used for background thread operations.
  
## Libraries Used
  
  * Retrofit
  * Koin
  * Mockk
  * RxJava
  * Coroutine
  * Glide
  
## Improvements/ Not Implemented

  * Code coverage can be improved with more unit test cases
  * Swipe to refresh
  * Local DB cache refresh based on business requirement
  * UI test cases
