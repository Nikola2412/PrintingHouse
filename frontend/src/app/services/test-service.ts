import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Message } from '../models/Message';

@Injectable({
  providedIn: 'root',
})
export class TestService {
  private path = 'http://localhost:8080/app';
  private http = inject(HttpClient);

  appTest(){
    return this.http.get<Message>(`${this.path}`);
  }
}
