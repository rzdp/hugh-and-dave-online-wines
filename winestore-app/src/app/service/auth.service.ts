import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {SignupRequest} from '../model/request/signup.request';
import {MessageResponse} from '../model/response/message.response';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) {
  }

  signup(request: SignupRequest) {
    return this.http.post<MessageResponse>('http://localhost:8080/auth/v1/sign-up', request);
  }

  notifySignup(userId: number) {
    return this.http.post<MessageResponse>('http://localhost:8080/auth/v1/sign-up/' + userId + '/notification', {});
  }

  confirmSignup(userId: number) {
    return this.http.post<MessageResponse>('http://localhost:8080/auth/v1/sign-up/' + userId + '/verification', {});
  }

}
