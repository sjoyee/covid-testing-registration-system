import Vue from "vue";
import VueRouter from "vue-router";
import LoginPage from "../components/LoginPage";
import SearchPage from "../components/SearchPage";
import ReceptionistPage from "../components/ReceptionistPage";
import OnsiteBooking from "../components/OnsiteBooking";
import CheckStatus from "../components/CheckStatus";
import OnsiteTesting from "../components/OnsiteTesting";
import CustomerPage from "../components/CustomerPage";
import CustomerCheckStatus from "../components/CustomerCheckStatus";
import HomeBooking from "../components/HomeBooking";
import ErrorPage from "../components/ErrorPage";

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
    path: "/:id/receptionist/check-status",
    component: CheckStatus,
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
    path: "/:id/onsite-testing",
    component: OnsiteTesting,
  },
  {
    path: "/error",
    component: ErrorPage,
  },
];

const router = new VueRouter({
  routes,
});

export default router;
