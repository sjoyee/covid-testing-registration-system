<template>
  <div>
    <v-toolbar dark>
      <v-toolbar-title>COVID-19 Testing Registration System</v-toolbar-title>
      <v-spacer></v-spacer>
      <v-btn text @click="backToMain">
        <v-icon left>mdi-arrow-left-bold</v-icon>
        BACK TO MAIN
      </v-btn>
      <v-btn text @click="search">
        <v-icon left>mdi-map-search</v-icon>
        SEARCH TESTING SITES
      </v-btn>
      <v-btn text @click="logout">LOG OUT</v-btn>
    </v-toolbar>
    <div class="ma-4">
      <v-row>
        <div class="text-h5 font-weight-bold pa-4">Admin Interface</div>
        <v-spacer></v-spacer>
        <div class="mx-8 my-4">
          <v-icon v-if="hasNotif" @click="showNotifDialog" color="error"
            >mdi-bell-badge</v-icon
          >
          <v-icon v-else>mdi-bell-outline</v-icon>
        </div>
      </v-row>
      <div>
        <v-dialog v-model="showNotif" width="700">
          <v-list flat>
            <v-list-item-group>
              <template v-for="notification in notificationList">
                <v-list-item :key="notification.id">
                  <v-list-item-content>
                    <div class="text-subtitle-1 pa-2">
                      <div>{{ notification }}</div>
                    </div>
                    <v-divider></v-divider>
                  </v-list-item-content>
                </v-list-item>
              </template>
              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn text @click="closeNotifDialog">BACK</v-btn>
              </v-card-actions>
            </v-list-item-group>
          </v-list>
        </v-dialog>
      </div>
      <div v-if="user" class="text-h6 pa-4">
        <p>User ID: {{ user.id }}</p>
        <p>User Name: {{ user.userName }}</p>
        <p>Testing Site Id: {{ user.testingSite.id }}</p>
        <p>Testing Site Name: {{ user.testingSite.name }}</p>
      </div>
      <div v-if="user" class="text-subtitle-1 px-4">
        The followings are all the bookings booked in
        {{ user.testingSite.name }}. Only Active Bookings can be modified.
      </div>
      <v-card flat tile class="ma-4" v-if="bookings">
        <v-list flat>
          <v-list-item-group>
            <template v-for="booking in bookings">
              <v-list-item :key="booking.id">
                <v-list-item-content>
                  <v-card class="ma-4 pa-4">
                    <div class="text-subtitle-1 pa-2">
                      <div>Booking ID: {{ booking.id }}</div>
                      <div>Testing Site: {{ booking.testingSite.name }}</div>
                      <div>
                        Datetime: {{ formatDateTime(booking.startTime) }}
                      </div>
                      <div>Booking Status: {{ booking.status }}</div>
                      <div>Active: {{ booking.isActive }}</div>
                    </div>
                    <v-card-actions v-if="booking.isActive">
                      <v-spacer></v-spacer>
                      <v-btn text @click="modify(booking)">Modify</v-btn>
                      <v-btn text @click="restore(booking)" color="primary"
                        >Restore</v-btn
                      >
                      <v-btn text @click="cancel(booking.id)" color="warning"
                        >Cancel</v-btn
                      >
                      <v-btn text @click="del(booking.id)" color="error"
                        >Delete</v-btn
                      >
                    </v-card-actions>
                  </v-card>
                </v-list-item-content>
              </v-list-item>
            </template>
          </v-list-item-group>
        </v-list>
      </v-card>
      <v-progress-circular
        v-else
        indeterminate
        class="ma-12"
      ></v-progress-circular>
    </div>

    <modify-dialog
      v-if="bookings && bookingDetails"
      v-model="openModifyDialog"
      :bookingDetails="bookingDetails"
      :testingSites="testingSites"
    ></modify-dialog>

    <restore-dialog
      v-if="bookings && bookingDetails"
      v-model="openRestoreDialog"
      :bookingDetails="bookingDetails"
      :testingSites="testingSites"
    ></restore-dialog>

    <v-snackbar v-model="errorCancel" :timeout="timeout" color="error">
      Fail to cancel booking.
      <template v-slot:action="{ attrs }">
        <v-btn text v-bind="attrs" @click="errorMessage = false"> Close </v-btn>
      </template>
    </v-snackbar>
    <v-snackbar v-model="successCancel" :timeout="timeout" color="success">
      Successfully cancel booking.
    </v-snackbar>
  </div>
</template>

<script>
import axios from "axios";
import moment from "moment";
import ModifyDialog from "@/components/ModifyDialog.vue";
import RestoreDialog from "@/components/RestoreDialog.vue";

export default {
  components: { ModifyDialog, RestoreDialog },
  data() {
    return {
      bookingForm: null,
      bookings: null,
      bookingDetails: null,
      successCancel: false,
      errorCancel: false,
      timeout: 2000,
      openModifyDialog: false,
      openRestoreDialog: false,
      activeBooking: null,
      sites: null,
      user: null,
      hasNotif: false,
      unreadNotif: false,
      showNotif: false,
      notificationList: [],
    };
  },
  mounted() {
    this.getUserDetails();
    this.filterTestingSites();
    window.setInterval(() => {
      this.getNotifications();
    }, 2000);
  },

  watch: {
    openModifyDialog() {
      this.getBookings(this.user.testingSite.id);
    },
    openRestoreDialog() {
      this.getBookings(this.user.testingSite.id);
    },
  },
  methods: {
    backToMain() {
      this.$router.push({
        path: `/${this.$route.params.id}/receptionist/`,
      });
    },
    search() {
      this.$router.push({
        path: `/${this.$route.params.id}/receptionist/search`,
      });
    },

    modify(booking) {
      this.bookingDetails = booking;
      this.openModifyDialog = true;
    },

    restore(booking) {
      this.bookingDetails = booking;
      this.openRestoreDialog = true;
    },

    async cancel(id) {
      try {
        await axios.post(
          `/${this.$route.params.id}/booking/active-booking/cancel?id=${id}`
        );
        this.successCancel = true;
        console.log(this.user);
        this.getBookings(this.user.testingSite.id);
      } catch {
        // handle error
        console.log("Fail to cancel booking.");
        this.errorCancel = true;
      }
    },

    async del(id) {
      try {
        await axios.delete(
          `/${this.$route.params.id}/booking/active-booking/delete?id=${id}`
        );
        this.getBookings(this.user.testingSite.id);
      } catch (e) {
        // handle error
        console.log(e);
        console.log("Fail to delete booking.");
      }
    },

    async getUserDetails() {
      try {
        const response = await axios.get(`/user/${this.$route.params.id}`);
        this.user = response.data;
        this.getBookings(this.user.testingSite.id);
      } catch {
        // handle error
        console.log("Fail to create user.");
      }
    },

    async filterTestingSites() {
      try {
        const response = await axios.get(`/testing-site?type=&suburb=`);
        this.sites = response.data;
      } catch {
        console.log("Fail to get testing sites.");
      }
    },

    async getBookings(testingSiteId) {
      try {
        const response = await axios.get(
          `/${this.$route.params.id}/booking/all?id=${testingSiteId}`
        );
        this.bookings = response.data;
      } catch {
        console.log("Fail to get the bookings.");
      }
    },

    async getNotifications() {
      try {
        const response = await axios.get(
          `/${this.$route.params.id}/booking/admin-notif`
        );
        if (response.data != null && response.data.length > 0) {
          if (
            this.notificationList.length == 0 ||
            (this.notificationList.length > 0 &&
              this.notificationList[this.notificationList.length - 1] !=
                response.data)
          ) {
            this.notificationList.push(response.data);
            this.hasNotif = true;
            this.unreadNotif = true;
            console.log(response.data);
          }
        }
      } catch (e) {
        console.log("Fail to get the notification.");
      }
    },

    async logout() {
      await this.$router.push({
        path: "/login",
      });
    },
    formatDateTime(datetime) {
      return moment(datetime, "YYYY-MM-DDTHH:mm:ssZ").format(
        "DD-MM-YYYY HH:mm"
      );
    },
    showNotifDialog() {
      this.showNotif = true;
      this.hasNotif = false;
    },

    closeNotifDialog() {
      this.showNotif = false;
    },
  },

  computed: {
    testingSites() {
      var testingSites = [];
      for (var item of this.sites) {
        testingSites.push({
          name: item.name,
          id: item.id,
        });
      }
      return testingSites;
    },
  },
};
</script>

<style></style>
