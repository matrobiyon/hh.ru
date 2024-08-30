package tj.example.effectivemobile.core

import android.util.Log
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

suspend fun <T> callGenericRequest(
    request: suspend () -> Response<T>,
): Flow<Resource<T?>> = flow {

    emit(Resource.Loading())

    try {
        var result = request()

        Log.d("TAG", "callGenericRequest: ${result.code()}")
        if (result.isSuccessful) {
            delay(500)
            emit(Resource.Success(result.body()!!))
        } else if (result.code() == 422 || result.code() == 400) {
            emit(
                Resource.Error(
                    message = result.errorBody()?.string() ?: "422 or 400 error"
                )
            )
        } else {
            emit(
                Resource.Error(
                    message = result.errorBody()?.string() ?: ""
                )
            )
        }
    } catch (e: IOException) {
        emit(Resource.Error(message = e.message))
    } catch (e: HttpException) {
        emit(Resource.Error(message = "Http exception..."))
    } catch (e: Exception) {
        emit(Resource.Error(message = "$e..."))
    }
}
