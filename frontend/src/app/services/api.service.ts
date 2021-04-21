import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpParams } from '@angular/common/http';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  constructor(
    private httpClient: HttpClient
  ) { }

  get(path: string, options?: any): Observable<any> {
    return this.httpClient.get(
      `${environment.serverUrl}${path}`,
      {
        ...options,
        params: new HttpParams(),
      }
    );
  }

  post(path: string, body: any = {}, options?: any): Observable<any> {
    return this.httpClient.post(`${environment.serverUrl}${path}`, body, options);
  }

  put(path: string, body: any = {}, options?: any): Observable<any> {
    return this.httpClient.put(`${environment.serverUrl}${path}`, body, options);
  }

  delete(path: string, options?: any): Observable<any> {
    return this.httpClient.delete(`${environment.serverUrl}${path}`, options);
  }
}
