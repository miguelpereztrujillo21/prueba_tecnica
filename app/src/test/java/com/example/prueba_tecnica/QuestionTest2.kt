package com.example.prueba_tecnica

import com.example.prueba_tecnica.models.Movie
import com.example.prueba_tecnica.models.MovieInPlatform
import com.example.prueba_tecnica.models.Platform
import org.junit.Test

class QuestionTest2 {

    private fun getMoviesInPlatforms(movies:List<Movie>, platforms:List<Platform>):List<MovieInPlatform>{
        val moviesInPlatform = mutableListOf<MovieInPlatform>()
        movies.forEach { movie ->
            val platform = platforms.find { it.movieId == movie.movieId }
            platform?.let {
                if(it.inService1||it.inService2){
                    moviesInPlatform.add(MovieInPlatform(movie, it))
                }
            }
        }
        return moviesInPlatform
    }


    @Test
    fun `test get movies in platforms result`(){
       val moviesList = listOf(
            Movie(movieId="Movie1",title="The movie 1"),
            Movie(movieId="Movie2",title="The movie 2"),
            Movie(movieId="Movie3",title="The movie 3"),
            Movie(movieId="Movie4",title="The movie 4")
        )
        val platformsList = listOf(
            Platform(movieId = "Movie1", inService1 = false, inService2 = false),
            Platform(movieId = "Movie2", inService1 = true, inService2 = false),
            Platform(movieId = "Movie4", inService1 = true, inService2 = true)
        )

        val testResult = getMoviesInPlatforms(moviesList,platformsList)

        assert(testResult[0].movie.movieId == "Movie2")
        assert(testResult[0].platform.movieId == "Movie2" )
        assert(testResult[0].platform.inService1)
        assert(!testResult[0].platform.inService2)
    }

}