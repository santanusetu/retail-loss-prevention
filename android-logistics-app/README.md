# LPARI Logistics: driver app

The Android app a delivery driver uses on a shift: today's drops grouped by loss risk, the details of each drop, a scan of every package before hand-off, and the customer's signature as proof of delivery. Part of the [Retail Loss Prevention](../README.md) project (SJSU, CMPE 295, 2016).

<p>
<img src="../docs/screenshots/logistics/02-high-alert-list.png" width="200" alt="High-alert drops">
<img src="../docs/screenshots/logistics/04-package-scan.png" width="200" alt="Package checklist">
<img src="../docs/screenshots/logistics/05-signature.png" width="200" alt="Customer signature">
<img src="../docs/screenshots/logistics/06-completed.png" width="200" alt="Completed deliveries">
</p>

## The delivery flow

1. **Sign in.**
2. **Drops by risk.** Pending drops are split into *Low caution*, *Medium alert* and *High alert* tabs, so the drops most likely to end in a loss claim stand out.
3. **Shipment details.** Customer, address, delivery instructions and priority. *Start delivery* opens turn-by-turn directions in Google Maps.
4. **Scan every package.** Each item must be scanned before hand-off. There's a manual-entry fallback for damaged labels.
5. **Proof of delivery.** The customer signs on screen, and the signature is saved with the drop.
6. **Completed.** The drop moves to the *Completed* list.

## Run it

Requirements: Android Studio (or JDK 17 and the Android SDK).

```bash
cd LPARILogisticApp
./gradlew installDebug      # with an emulator or device connected
```

It starts in **demo mode**. Every API call is answered from [`app/src/main/assets/demo/`](LPARILogisticApp/app/src/main/assets/demo) (fictional customers, `555` phone numbers), so it runs without a backend. Any email address and password will sign you in.

To use a real backend and a map preview, add these lines to `LPARILogisticApp/local.properties` (the file is git-ignored):

```properties
api.baseUrl=https://your-api.example.com/
maps.apiKey=YOUR_GOOGLE_MAPS_STATIC_API_KEY
```

The API contract is in [`RetrofitApi.java`](LPARILogisticApp/app/src/main/java/com/sjsu/cmpe273/lparilogisticapp/data/RetrofitApi.java): `POST /sessions`, `POST /users`, `GET /trips`.

## What changed in the 2026 modernization

The 2016 app no longer built: support libraries, beta Retrofit, Gradle 2. Its servers are gone too. Beyond getting it to build, the upgrade fixed things that would not pass a code review today.

| Area | 2016 | Now |
|---|---|---|
| Toolchain | Gradle 2, SDK 23, support libraries, Retrofit 2 beta | Gradle 8.7, AGP 8.5, target SDK 34, AndroidX, Retrofit 2.9 + OkHttp 4 |
| Credentials | Password put into a plain-HTTP URL path and printed to the log | Sent in the request body; nothing logged |
| Login | A 3-second timer that always "succeeded" | Real request, real success and failure handling |
| Sign-up validation | "Confirm password" read from the phone field; matching passwords rejected | Correct checks for email, password match, ZIP and phone |
| Customer contact | Texts sent silently in the background; `ACTION_CALL` with no permission (crash) | Opens the dialer and messaging app for the driver to confirm, with no phone or SMS permission |
| Completed tab | Filtered for *pending* drops | Shows delivered drops |
| Code structure | 5 copies of the list screen and 5 copies of the adapter, static caches | 1 base screen, 1 adapter, 1 repository |
| Networking | Map geocoding on the UI thread; `AsyncTask` | Background executor; Maps opened by intent |
| Permissions | 8 requested, none at runtime | 2 (internet, camera); camera requested at runtime |
| Signature storage | Public Pictures folder, blocked on Android 10+ | App-private storage |
