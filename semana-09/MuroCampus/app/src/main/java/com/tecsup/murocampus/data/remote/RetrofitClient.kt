private val cliente = OkHttpClient.Builder()
    .addInterceptor { chain ->
        val peticion = chain.request().newBuilder()
            .header("Accept", "application/json")
            .build()
        chain.proceed(peticion)
    }
    .addInterceptor(HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    })
    .build()