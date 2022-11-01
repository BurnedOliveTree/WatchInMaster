import {Observable} from "rxjs";
import {PageRequest, PageResponse} from "../../../../generated/dto";

export type DataLoader<T, F> = (pageRequest: PageRequest<F>) => Observable<PageResponse<T>>