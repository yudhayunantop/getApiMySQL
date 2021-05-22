package com.example.myapplication

data class Konfigurasi (
    val URL_GET_ALL : String = "http://192.168.100.8/web/apikado/tampilPgw.php",
    val URL_GET_ID : String = "http://192.168.100.8/web/apikado/tampilId.php?id=",

    //Dibawah ini merupakan script atau perintah untuk mengirim permintaan ke dalam Skrip PHP
    val KEY_EMP_ID : String = "idaturanB5",
    val KEY_EMP_EXTRA : String = "extra",
    val KEY_EMP_AGREE : String = "agree",
    val KEY_EMP_CONS : String = "cons",
    val KEY_EMP_NEURO : String = "neuro",
    val KEY_EMP_OPEN : String = "open",

    //JSON Tags
    val TAG_JSON_ARRAY : String = "result",
    val TAG_ID : String = "idaturanB5",
    val TAG_EXTRA : String = "extra",
    val TAG_AGREE : String = "agree",
    val TAG_CONS : String = "cons",
    val TAG_NEURO : String = "neuro",
    val TAG_OPEN : String = "open",

)
