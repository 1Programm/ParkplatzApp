import { registerLocaleData } from '@angular/common';
import localeDE from '@angular/common/locales/de';
import localeDeExtra from '@angular/common/locales/extra/de';
import { NgModule } from '@angular/core';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { FlexLayoutModule } from '@angular/flex-layout';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {
  LuxActionModule,
  LuxMarkdownModule,
  LuxCommonModule,
  LuxComponentsConfigModule,
  LuxComponentsConfigParameters,
  LuxDirectivesModule,
  LuxErrorModule,
  LuxFormModule,
  LuxIconModule,
  LuxLayoutModule,
  LuxPipesModule,
  LuxPopupsModule
} from '@ihk-gfi/lux-components';
import { environment } from '../environments/environment';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { PageHomeComponent } from './components/pages/page-home/page-home.component';
import { PageErrorComponent } from './components/pages/page-error/page-error.component';
import { BuchenPageComponent } from './components/pages/buchen-page/buchen-page.component';
import { PageProfilComponent } from './components/pages/page-profil/page-profil.component';
import { EditListComponent } from './components/core/edit-list/edit-list.component';
import { PageBuchungsuebersichtComponent } from './components/pages/page-buchungsuebersicht/page-buchungsuebersicht.component';
import { MapComponent } from './components/core/map/map.component';
import { MarkerDialogComponent } from './components/dialogs/marker-dialog/marker-dialog.component';
import { AdminEditComponent } from './components/core/admin-edit/admin-edit.component';
import { PageVerstossComponent } from './components/pages/page-verstoss/page-verstoss.component';
import { JsonDateInterceptor } from './interceptors/json-date.interceptor';
import { PageBuchungsuebersichtAdminComponent } from './components/pages/page-buchungsuebersicht-admin/page-buchungsuebersicht-admin.component';
import { ParkplatzDatePipe } from './pipes/pa-date.pipe';
import { BuchungsuebersichtAdminViewDateComponent } from './components/pages/page-buchungsuebersicht-admin/view-date/view-date.component';
import { BuchungsuebersichtAdminViewAllComponent } from './components/pages/page-buchungsuebersicht-admin/view-all/view-all.component';
import { BuchungsuebersichtAdminViewMitarbeiterComponent } from './components/pages/page-buchungsuebersicht-admin/view-mitarbeiter/view-mitarbeiter.component';
import { EditParkhausDialogComponent } from './components/dialogs/edit-parkhaus-dialog/edit-parkhaus-dialog.component';
import { GooglePlaceModule } from "ngx-google-places-autocomplete";
import { KennzeichenHinzufuegenDialogComponent } from './components/dialogs/kennzeichen-hinzufuegen-dialog/kennzeichen-hinzufuegen-dialog.component';


registerLocaleData(localeDE, localeDeExtra);

const luxComponentsConfig: LuxComponentsConfigParameters = {
  generateLuxTagIds: environment.generateLuxTagIds,
};

@NgModule({
  declarations: [
    AppComponent,
    PageHomeComponent,
    PageErrorComponent,
    PageProfilComponent,
    EditListComponent,
    PageBuchungsuebersichtComponent,
    EditListComponent,
    BuchenPageComponent,
    MapComponent,
    MarkerDialogComponent,
    PageBuchungsuebersichtAdminComponent,
    ParkplatzDatePipe,
    BuchungsuebersichtAdminViewDateComponent,
    BuchungsuebersichtAdminViewAllComponent,
    BuchungsuebersichtAdminViewMitarbeiterComponent,
    AdminEditComponent,
    PageVerstossComponent,
    EditParkhausDialogComponent,
    KennzeichenHinzufuegenDialogComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    HttpClientModule,
    LuxDirectivesModule,
    LuxIconModule,
    LuxLayoutModule,
    LuxActionModule,
    LuxFormModule,
    LuxCommonModule,
    LuxPipesModule,
    LuxPopupsModule,
    LuxErrorModule,
    LuxMarkdownModule,
    FlexLayoutModule,
    GooglePlaceModule,

    LuxComponentsConfigModule.forRoot(luxComponentsConfig)
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: JsonDateInterceptor, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule {}
