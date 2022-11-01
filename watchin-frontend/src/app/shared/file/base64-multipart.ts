import {Observable, of} from "rxjs";
import {fromFetch} from "rxjs/fetch";
import {catchError, map, mergeMap} from "rxjs/operators";

export function multipartFromBase64(base64: string, parameter: string): Observable<FormData | null> {
  return fromFetch(base64).pipe(
    mergeMap(response => response.blob()),
    map(blob => {
      const payload = new FormData();
      const file = new File([blob], parameter);
      payload.append(parameter, file);
      return payload;
    }),
    catchError(() => of(null)),
  );
}
