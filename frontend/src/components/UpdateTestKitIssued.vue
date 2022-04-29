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
      <div class="text-h5 font-weight-bold pa-4">
        Update Issued Test Kit Information
      </div>
      <div class="text-subtitle-1 pa-4">
        For updating the system that the users have received the RAT kit from
        the facility.
      </div>

      <v-row>
        <v-col md="6">
          <v-card class="ma-4 pa-4" tile>
            <v-card-subtitle
              >Please request the QR code from the person who wants to collect
              the RAT kit for verification.</v-card-subtitle
            >
            <v-form ref="form" v-model="valid">
              <v-row class="pa-4">
                <v-col md="6">
                  <v-text-field
                    v-model="qrCode"
                    label="QR Code"
                    :rules="mandatoryRule"
                  ></v-text-field>
                </v-col>
              </v-row>
            </v-form>
            <v-card-actions class="pa-4">
              <v-spacer></v-spacer>
              <v-btn :disabled="!valid" @click="checkQr">UPDATE</v-btn>
            </v-card-actions>
          </v-card>
        </v-col>
      </v-row>
    </div>
    <v-snackbar v-model="success" :timeout="timeout" color="success">
      Successfully updated.
    </v-snackbar>
    <v-snackbar v-model="errorMessage" :timeout="timeout" color="error">
      Invalid request of RAT kit collection.
      <template v-slot:action="{ attrs }">
        <v-btn text v-bind="attrs" @click="errorMessage = false"> Close </v-btn>
      </template>
    </v-snackbar>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      form: null,
      valid: false,
      qrCode: "",
      success: false,
      errorMessage: false,
      timeout: 2500,
      status: "",
    };
  },
  methods: {
    search() {
      this.$router.push({
        path: `/${this.$route.params.id}/receptionist/search`,
      });
    },
    backToMain() {
      this.$router.push({
        path: `/${this.$route.params.id}/receptionist/`,
      });
    },
    logout() {
      this.$router.push({
        path: "/login",
      });
    },
    async checkQr() {
      try {
        await axios.post(`/user/update-test-kit-issued?qrCode=${this.qrCode}`);
        this.success = true;
      } catch {
        // handle error
        console.log("Fail to update test kit issued with this QR code.");
        this.errorMessage = true;
      }
      this.$refs.form.reset();
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
