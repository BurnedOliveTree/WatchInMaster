import {Component, EventEmitter, Output} from '@angular/core';
import {ResponsiveLayoutService} from '../../shared/layout/responsive-layout.service';
import {DateFilter, DurationFilter, SortOrder, VideoSearchFilterDTO} from "../../../../generated/dto";

@Component({
  selector: 'public-video-filter',
  templateUrl: './video-filter.component.html',
  styleUrls: ['./video-filter.component.scss']
})
export class VideoFilterComponent {

  @Output()
  readonly filterSaved = new EventEmitter<VideoSearchFilterDTO>();

  readonly sortOptions = [
    { label: 'Trafność', value: SortOrder.RELEVANCE },
    { label: 'Data przesłania', value: SortOrder.DATE },
    { label: 'Liczba wyświetleń', value: SortOrder.VIEWS }
  ];

  readonly dateOptions = [
    { label: 'Ostatnia godzina', value: DateFilter.HOUR },
    { label: 'Dzisiaj', value: DateFilter.DAY },
    { label: 'Ten tydzień', value: DateFilter.WEEK },
    { label: 'Ten miesiąc', value: DateFilter.MONTH },
    { label: 'Ten rok', value: DateFilter.YEAR }
  ];

  readonly durationOptions = [
    { label: 'Poniżej 3 minut', value: DurationFilter.SHORT },
    { label: 'Od 3 do 15 minut', value: DurationFilter.MEDIUM },
    { label: 'Powyżej 15 minut', value: DurationFilter.LONG }
  ];

  filter: VideoSearchFilterDTO = {
    phrase: null,
    channel: null,
    sort: SortOrder.RELEVANCE,
    date: null,
    duration: null
  };

  constructor(public responsiveLayoutService: ResponsiveLayoutService) {
  }

  submit() {
    this.filterSaved.emit(this.filter);
  }
}
