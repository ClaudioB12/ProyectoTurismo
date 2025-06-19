import 'package:dio/dio.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:frontendturismoflutter/utils/constants.dart';

class TokenInterceptor extends Interceptor {
  final FlutterSecureStorage _storage = const FlutterSecureStorage();

  @override
  void onRequest(RequestOptions options, RequestInterceptorHandler handler) async {
    final token = await _storage.read(key: 'jwt');

    if (token != null && token.isNotEmpty) {
      options.headers['Authorization'] = 'Bearer $token';
    }

    return super.onRequest(options, handler);
  }

  Dio crearDioConInterceptor() {
    final dio = Dio(BaseOptions(
      baseUrl: AppConstants.baseUrl, // ¡Usa constante estática correctamente!
      connectTimeout: const Duration(seconds: 10),
      receiveTimeout: const Duration(seconds: 10),
      contentType: 'application/json',
    ));

    dio.interceptors.add(TokenInterceptor());

    return dio;
  }
}
