<template>
  <div>
    <v-toolbar dark>
      <v-toolbar-title>COVID-19 Testing Registration System</v-toolbar-title>
      <v-spacer></v-spacer>
      <v-btn text @click="search">
        <v-icon left>mdi-map-search</v-icon>
        SEARCH TESTING SITES
      </v-btn>
    </v-toolbar>
    <v-card class="justify-center pt-12" flat tile>
      <v-row class="justify-center ma-12" flat tile>
        <v-img
          src="https://cdn-icons-png.flaticon.com/512/784/784095.png"
          max-height="150"
          max-width="120"
        ></v-img>
      </v-row>
      <v-form ref="loginForm" v-model="valid">
        <v-row class="justify-center">
          <v-col cols="4">
            <v-text-field
              v-model="userName"
              label="Username"
              outlined
              :rules="mandatoryRule"
            ></v-text-field>
          </v-col>
        </v-row>
        <v-row class="justify-center">
          <v-col cols="4">
            <v-text-field
              v-model="password"
              label="Password"
              type="password"
              outlined
              :rules="mandatoryRule"
            ></v-text-field>
          </v-col>
        </v-row>
      </v-form>
      <v-card-actions>
        <v-row class="justify-center ma-12">
          <v-btn :disabled="!valid" @click="login()">LOG IN</v-btn>
        </v-row>
      </v-card-actions>
    </v-card>
    <v-snackbar v-model="errorMessage" :timeout="timeout">
      Please enter the correct login details.
      <template v-slot:action="{ attrs }">
        <v-btn color="red" text v-bind="attrs" @click="errorMessage = false">
          Close
        </v-btn>
      </template>
    </v-snackbar>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "LoginPage",

  data() {
    return {
      valid: false,
      userName: "",
      password: "",
      errorMessage: false,
      timeout: 2000,
      userDetails: null,
    };
  },

  methods: {
    async login() {
      try {
        const response = await axios.post(
          `/user/login?userName=${this.userName}&password=${this.password}`
        );
        this.userDetails = response.data;
        if (this.userDetails.isCustomer) {
          this.$router.push({ path: `/${this.userDetails.id}/customer` });
        } else if (this.userDetails.isReceptionist) {
          this.$router.push({
            path: `/${this.userDetails.id}/receptionist`,
          });
        } else if (this.userDetails.isHealthcareWorker) {
          this.$router.push({ path: `/${this.userDetails.id}/hcworker` });
        } else {
          this.errorMessage = true;
        }
      } catch {
        // handle error
        console.log("Wrong username and password");
        this.errorMessage = true;
      }
    },

    search() {
      this.$router.push({ path: "/search" });
    },
  },

  computed: {
    mandatoryRule() {
      return [(v) => !!v || "This field is mandatory."];
    },
  },
};
</script>

<style></style>
