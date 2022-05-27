import Vue from "vue";
import VueRouter from "vue-router";
import LoginPage from "../view/common/LoginPage";
import SearchPage from "../view/common/SearchPage";
import ReceptionistPage from "../view/receptionist/ReceptionistPage";
import OnsiteBooking from "../view/receptionist/OnsiteBooking";
import CheckStatus from "../view/receptionist/CheckStatus";
import PhoneCallProfile from "../view/receptionist/PhoneCallProfile";
import AdminInterface from "../view/receptionist/AdminInterface";
import BookingProfile from "../view/customer/BookingProfile";
import ModifyBooking from "../view/customer/ModifyBooking";
import OnsiteTesting from "../view/healthcareworker/OnsiteTesting";
import CustomerPage from "../view/customer/CustomerPage";
import CustomerCheckStatus from "../view/customer/CustomerCheckStatus";
import HomeBooking from "../view/customer/HomeBooking";
import UpdateTestKitIssued from "../view/receptionist/UpdateTestKitIssued";
import HealthcareWorker from "../view/healthcareworker/HealthcareWorker";

Vue.use(VueRouter);

const routes = [
  {
    path: "/login",
    component: LoginPage,
  },
  {
    path: "/",
    component: SearchPage,
  },
  {
    path: "/search",
    component: SearchPage,
  },
  {
    path: "/:id/:role/search",
    component: SearchPage,
  },
  {
    path: "/:id/receptionist",
    component: ReceptionistPage,
  },
  {
    path: "/:id/receptionist/onsite-booking",
    component: OnsiteBooking,
  },
  {
    path: "/:id/receptionist/phone-call-profile",
    component: PhoneCallProfile,
  },
  {
    path: "/:id/receptionist/admin-interface",
    component: AdminInterface,
  },
  {
    path: "/:id/receptionist/check-status",
    component: CheckStatus,
  },
  {
    path: "/:id/receptionist/update-test-kit-issued",
    component: UpdateTestKitIssued,
  },
  {
    path: "/:id/customer",
    component: CustomerPage,
  },
  {
    path: "/:id/customer/check-status",
    component: CustomerCheckStatus,
  },
  {
    path: "/:id/customer/home-booking",
    component: HomeBooking,
  },
  {
    path: "/:id/customer/booking-profile",
    component: BookingProfile,
  },
  {
    path: "/:id/customer/modify-booking",
    component: ModifyBooking,
  },
  {
    path: "/:id/hcworker/",
    component: HealthcareWorker,
  },
  {
    path: "/:id/hcworker/onsite-testing",
    component: OnsiteTesting,
  },
];

const router = new VueRouter({
  routes,
});

export default router;
