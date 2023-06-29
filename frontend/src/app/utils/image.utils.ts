import { Observable, Subject } from "rxjs";

export class ImageUtils {

    public static readAsDataUrl(imageBlob): Observable<string>{
        let imageLoadedSubject = new Subject<string>();
        
        const reader = new FileReader();
        reader.onload = (e) => {
            imageLoadedSubject.next(e.target.result as string);
        }
        reader.readAsDataURL(new Blob([imageBlob]));

        return imageLoadedSubject;
    }

}