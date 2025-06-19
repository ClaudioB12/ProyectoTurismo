import 'package:dio/dio.dart';

class DioClient {
  final Dio dio;

  DioClient._internal(this.dio);

  factory DioClient(String baseUrl, {Dio? dio, String? token}) {
    final dioInstance = dio ?? Dio(BaseOptions(baseUrl: baseUrl));

    if (token != null) {
      dioInstance.interceptors.add(InterceptorsWrapper(
        onRequest: (options, handler) {
          options.headers['Authorization'] = 'Bearer $token';
          return handler.next(options);
        },
      ));
    }

    return DioClient._internal(dioInstance);
  }
}
