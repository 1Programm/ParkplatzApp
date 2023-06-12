import { registerLocaleData } from '@angular/common';
import localeDE from '@angular/common/locales/de';
import localeDeExtra from '@angular/common/locales/extra/de';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
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
import { PageTestComponent } from './components/pages/page-test/page-test.component';
import { EditListComponent } from './components/core/edit-list/edit-list.component';

registerLocaleData(localeDE, localeDeExtra);

const luxComponentsConfig: LuxComponentsConfigParameters = {
  generateLuxTagIds: environment.generateLuxTagIds,
};

@NgModule({
  declarations: [
    AppComponent,
    PageHomeComponent,
    PageErrorComponent,
    PageTestComponent,
    PageProfilComponent,
    EditListComponent,
    BuchenPageComponent,
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
    LuxComponentsConfigModule.forRoot(luxComponentsConfig)
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {}
