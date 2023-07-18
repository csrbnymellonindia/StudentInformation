import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import {MatInputModule} from '@angular/material/input'; import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MatTabsModule } from '@angular/material/tabs';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatButtonModule } from '@angular/material/button'
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatMenuModule } from '@angular/material/menu';
import { MatIconModule } from '@angular/material/icon';
import { MatDividerModule } from '@angular/material/divider';
import { MatListModule } from '@angular/material/list';
import { LoginRegisterComponent } from './login-register/login-register.component';
import {MatFormFieldModule} from '@angular/material/form-field';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { SharedModule } from "./shared/shared.module";


@NgModule({
    declarations: [
        AppComponent,
        LoginRegisterComponent,

    ],
    providers: [],
    bootstrap: [AppComponent],
    imports: [
        BrowserModule,
        MatTabsModule,
        AppRoutingModule,
        BrowserAnimationsModule,
        MatSidenavModule,
        FormsModule, ReactiveFormsModule,
        MatToolbarModule,
        MatFormFieldModule,
        MatMenuModule,
        MatIconModule,
        MatDividerModule,
        MatListModule,
        MatInputModule,
        MatButtonModule,
        HttpClientModule,
        SharedModule
    ]
})
export class AppModule {}
