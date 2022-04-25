import Vue from 'vue'
import VueRouter from 'vue-router';
import LoginPage from '../components/LoginPage'
import SearchPage from '../components/SearchPage'
import OnsiteBooking from '../components/OnsiteBooking'
import OnsiteTesting from '../components/OnsiteTesting'
import HomeBooking from '../components/HomeBooking'
import ErrorPage from '../components/ErrorPage'

Vue.use(VueRouter)

const routes = [
  {
      path: "/login",
      component: LoginPage
  },
  {
      path: "/",
      component: SearchPage
  },
  {
      path: "/search",
      component: SearchPage
  },
  {
      path: "/:id/search",
      component: SearchPage
  },
  {
      path: "/:id/onsite-booking",
      component: OnsiteBooking
  },
  {
      path: "/:id/home-booking",
      component: HomeBooking
  },
  {
      path: "/:id/onsite-testing",
      component: OnsiteTesting
  },
  {
      path: "/error",
      component: ErrorPage
  },
];

const router = new VueRouter({
  routes
})

export default router
