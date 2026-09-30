<h1 align="center">📦 Retail Loss Prevention (LPARI)</h1>

<p align="center">
  <b>Loss Prevention Analytics for the Retail Industry</b><br>
  A delivery-loss detection system: a driver app that proves every hand-off,<br>
  a backend that flags risky shipments and zones, and dashboards for the operations team.
</p>

<p align="center">
  <img alt="M.S. project" src="https://img.shields.io/badge/SJSU-M.S._project_2016-0055A2">
  <img alt="Android" src="https://img.shields.io/badge/Android-Java-3DDC84?logo=android&logoColor=white">
  <img alt="Backend" src="https://img.shields.io/badge/Backend-JAX--RS_%2B_MySQL-4479A1?logo=mysql&logoColor=white">
  <img alt="AWS" src="https://img.shields.io/badge/AWS-Elastic_Beanstalk-FF9900?logo=amazonaws&logoColor=white">
</p>

## The problem

Packages go missing between the warehouse and the customer's door: lost in transit, mis-delivered, or claimed as "never arrived". Without proof at each hand-off, a retailer can't tell a genuine loss from a fraudulent claim, and can't see where losses cluster.

LPARI records evidence at every step of the delivery and turns it into risk signals.

## How it works

```mermaid
flowchart LR
    W[Warehouse loads shipment] -->|barcode scan| D[Driver app]
    D -->|GPS route + tracking| C[Customer hand-off]
    C -->|barcode scan + signature| P[Proof of delivery]
    D & P --> B[Backend services]
    B --> R{Risk signals}
    R --> H[High alert shipments]
    R --> Z[Zones with reported losses]
    R --> U[Users with repeated losses]
    H & Z & U --> A[Operations dashboards]
```

- **Proof at every hand-off**: packages are barcode-scanned at loading and delivery, and the customer signs on the device.
- **Location evidence**: GPS tracking and map routing for each trip.
- **Risk tiers**: shipments are sorted into **high alert**, **medium alert** and **low caution** so drivers and operations focus on the risky ones first.
- **Loss analytics**: the backend surfaces **zones with reported package losses** and **users with repeated losses**, the patterns that separate real loss from fraud.

## What's in this repository

Five repositories from the project, combined here with their full commit history.

| Folder | What it is | Built by |
|---|---|---|
| [`android-logistics-app`](android-logistics-app) | The full driver app: trip list, shipments grouped by alert level (high, medium, low), shipment details, barcode scanning, signature capture, GPS routing, analytics charts, backend calls via Retrofit | Santanu Chakraborty |
| [`android-delivery-app-v1`](android-delivery-app-v1) | First version of the delivery app: sign-up and login, shipment list, item-by-item delivery with slide-to-confirm, barcode scanning, signature capture, GPS | Santanu Chakraborty |
| [`backend-services`](backend-services) | JAX-RS REST services over MySQL: package status, delivery history, alert zones, red-alert users | Nagashruthi |
| [`admin-dashboard`](admin-dashboard) | Operations dashboard, built on the open-source *SuperAdmin* template | Santanu Chakraborty |
| [`web-dashboard`](web-dashboard) | Web dashboard for the CMPE 295B demo, built on an open-source admin template | Santanu Chakraborty |

## Tech stack

| Layer | Technology |
|---|---|
| Mobile | Android (Java), Retrofit + OkHttp, Gson, ZXing barcode scanner, SignaturePad, Android LocationManager, Google Maps, MPAndroidChart |
| Backend | Java, JAX-RS (Jersey), MySQL, Maven |
| Cloud | AWS Elastic Beanstalk (sign-up service); mocky.io mock APIs during development |
| Web | HTML, CSS, JavaScript, Bootstrap admin templates |

## Status

This was the capstone project for the M.S. in Software Engineering at San José State University (CMPE 295A/B), completed in 2016. It is kept here as a record of that work and is **not maintained**: the Android build targets 2016 SDKs, and the backend expects a local MySQL database.

Looking back, the parts I'd change first are the ones that matter most at scale: move database credentials into configuration, replace the hard-coded alert tiers with a scoring model, and stream delivery events instead of polling for them. Real-time risk decisioning is what I went on to build professionally.

## Credits

Built at San José State University with **Nagashruthi**, who wrote the backend services (26 of the 29 commits in `backend-services`). The two dashboards are built on open-source admin templates, whose original licenses apply to that template code.
