import { HttpClient } from '@angular/common/http';
import { Component, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { LuxThemeService } from '@ihk-gfi/lux-components';
import { LuxSideNavComponent } from '@ihk-gfi/lux-components';
import { AccountService } from './services/account.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {

  @ViewChild(LuxSideNavComponent) sideNavComp!: LuxSideNavComponent;

  constructor(public accountService: AccountService, public router: Router, themeService: LuxThemeService, http: HttpClient) {
    themeService.loadTheme();
    router.initialNavigation();

    accountService.setup();
  }

}
