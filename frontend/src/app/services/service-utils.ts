import { Observable, catchError, retry, throwError } from "rxjs";

export class ServiceBase {

    protected wrapRetryAndCatchError<T>(o: Observable<T>): Observable<T> {
        return o.pipe(
          retry(1),
          catchError(this.handleError)
        );
    }

    // Error handling
    private handleError(error: any) {
        let errorMessage = '';
        if (error.error instanceof ErrorEvent) {
            // Get client-side error
            errorMessage = error.error.message;
        } else {
            // Get server-side error
            errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
        }

        window.alert(errorMessage);
        return throwError(() => new Error(errorMessage));
    }

}