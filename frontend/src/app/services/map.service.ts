import { Injectable } from '@angular/core';
import { ServiceBase } from './service-utils';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class MapService extends ServiceBase {

  constructor(private http: HttpClient) {
    super();
  }

}
