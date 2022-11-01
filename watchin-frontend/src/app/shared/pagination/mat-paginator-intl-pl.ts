import {Injectable} from "@angular/core";
import {MatPaginatorIntl} from "@angular/material/paginator";

@Injectable()
export class MatPaginatorIntlPl extends MatPaginatorIntl {

  readonly itemsPerPageLabel = 'Rozmiar strony';

  readonly nextPageLabel = 'Następna strona';

  readonly previousPageLabel = 'Poprzednia strona';

  readonly firstPageLabel = 'Pierwsza strona';

  readonly lastPageLabel = 'Ostatnia strona';

  getRangeLabel = function(page: number, pageSize: number, length: number): string {
    if (length === 0) {
      return '0 z 0'
    }

    const first = page * pageSize;
    const last = Math.min(first + pageSize, length);
    return `${first + 1} – ${last} z ${length}`;
  }
}