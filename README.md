# RorpDevAndroidLibs
Library for Android project development using Kotlin language.
Here below list of how to use libraries for Android development, the majority being actively maintained.
## Index
[BaseActivity](https://github.com/Rorp-Dev/RorpDevAndroidLibs#BaseActivity)
### BaseActivity
Your activity should have viewmodel otherwise you can use **__BaseViewModel::class.java__**
```
class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>(MainViewModel::class.java) {
  super.onCreate(savedInstanceState)
}
```
