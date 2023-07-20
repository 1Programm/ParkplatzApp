import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

//Json parser cannot parse date objects
//This class will intercept all calls and parse all date strings to date objects.
export class JsonDateInterceptor implements HttpInterceptor {
    private _isoDateFormat = /^\d{4}-\d{2}-\d{2}T\d{2}:\d{2}:\d{2}(?:\.\d*)?\+(?:\d{2}:\d{2})$/;
  
    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        return next.handle(req).pipe(
            map((val: HttpEvent<any>) => {
                if (val instanceof HttpResponse){
                    const body = val.body;
                    this.convert(body);
                }
                
                return val;
            })
        );
    }

    private convert(body: any){
        if (body === null || body === undefined ) {
            return body;
        }
        if (typeof body !== 'object' ){
            return body;
        }
        for (const key of Object.keys(body)) {
            const value = body[key];
            if (this.isDate(value)) {
                body[key] = new Date(value);
            } else if (typeof value === 'object') {
                this.convert(value);
            }
        }
    }

    private isDate(value: any): boolean {
        return /^(19|20)\d\d[- /.](0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])$/.test(value);
    }

    private isIsoDateString(value: any): boolean {
        console.log(">>>>>  TEST", value);
        if(value === null || value === undefined) return false;
        console.log("A");
        return typeof value === 'string' && this._isoDateFormat.test(value);
    }
}