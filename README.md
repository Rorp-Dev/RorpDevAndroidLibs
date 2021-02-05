# RorpDevAndroidLibs
Library for Android project development using Kotlin language.
## Index
Here below list of how to use libraries for Android development, the majority being actively maintained.

[1. BaseActivity](https://github.com/Rorp-Dev/RorpDevAndroidLibs#BaseActivity)
[2. Internet indicator](https://github.com/Rorp-Dev/RorpDevAndroidLibs#NetworkCallBack)
### BaseActivity
Your activity should have viewmodel otherwise you can use **__BaseViewModel::class.java__**
```
class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>(MainViewModel::class.java) {
  super.onCreate(savedInstanceState)
}
```
### NetworkCallBack
Checking/observing internet status online/offline. You must do as following : 
1. create NetworkCallback instance
2. register callback in onResume() and onPause()
3. call method **__observeNetworkConnection()__** for observing internet status and you can do whatever you want here.
3. unregister in onDestroy()
```
  var networkCallback = NetworkCallback(this)
  networkCallback.observeNetworkConnection(object : NetworkCallback.NetworkObservation{
            override fun isActive(isOnline: Boolean) {
                if(isOnline){
                    InternetSnackbar.internet(this@BaseActivity, mBaseBinding.root, "Back to online", InternetSnackbar.ONLINE)
                }else{
                    InternetSnackbar.internet(this@BaseActivity, mBaseBinding.root, "No internet", InternetSnackbar.OFFLINE)
                }
            }
        })
        
   override fun onResume() {
        super.onResume()
        // register network callback
        networkCallback.registerNetworkCallback()
   }
   
   override fun onPause() {
        // register network callback
        networkCallback.registerNetworkCallback()

        super.onPause()
    }
    
    override fun onDestroy() {
        // unregister network callback
        networkCallback.unregisterNetworkCallback()

        super.onDestroy()
    }
```
