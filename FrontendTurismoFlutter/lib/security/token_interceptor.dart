import 'package:dio/dio.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';

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
}