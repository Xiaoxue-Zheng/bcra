import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';

@Injectable({ providedIn: 'root' })
export class AuthServerProvider {
  constructor(private http: HttpClient) {}

  login(credentials): Observable<any> {
    const source = 'ADMIN';
    const data =
      `username=${encodeURIComponent(credentials.username)}` +
      `&password=${encodeURIComponent(credentials.password)}` +
      `&pin=${encodeURIComponent(credentials.pin)}` +
      `&remember-me=${credentials.rememberMe}` +
      `&submit=Login` +
      `&source=${source}`;
    const headers = new HttpHeaders().set('Content-Type', 'application/x-www-form-urlencoded');

    return this.http.post(SERVER_API_URL + 'api/authentication', data, { headers });
  }

  logout(): Observable<any> {
    // logout from the server
    return this.http.post(SERVER_API_URL + 'api/logout', {}, { observe: 'response' }).pipe(
      map((response: HttpResponse<any>) => {
        // to get a new csrf token call the api
        this.http.get(SERVER_API_URL + 'api/account').subscribe(() => {}, () => {});
        return response;
      })
    );
  }

  twoFactorAuthInit(login): Observable<any> {
    return this.http.post(SERVER_API_URL + 'api/authenticate/two-factor-init', login);
  }
}
