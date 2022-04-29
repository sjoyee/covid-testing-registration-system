<template>
  <div>
    <v-toolbar dark>
      <v-toolbar-title>COVID-19 Testing Registration System</v-toolbar-title>
      <v-spacer></v-spacer>
      
      <v-btn @click="logout" text>LOG OUT</v-btn>
    </v-toolbar>
    <div class="ma-4">
      <div class="text-h5 font-weight-bold pa-4">On-Site Testing</div>
      <v-col md="8">
        <v-card class="ma-4 pa-4" tile>
          <v-card-title>Covid Test Suggestion</v-card-title>
          <div class="pa-4">
            <v-form ref="customerForm" v-model="valid">
              <v-row>
                <v-col md="6">
                  <p>1. Are you exhibiting 2 or more symptoms as listed below? </p>
                  <ul><li>Fever</li>
                  <li>Chills</li>
                  <li>Shivering</li>
                  <li>Body Ache</li>
                  <li>Sore throat</li>
                  <li>Nausea or vomiting</li></ul>
                </v-col>
                <v-col md="6">
                  <input type="checkbox" id="checkbox" v-model="checked">
                  <label for="checkbox">{{ checked }}</label>
                    
                </v-col>
              </v-row>
              <v-row>
                <v-col md="6">
                   
                  <p>2. Besides the above, are you exhibiting any of the symptoms listed below? </p>
                <ul><li>Difficulty breathing</li>
                  <li>Loss of smell</li>
                  <li>Cough</li>
                  <li>Loss of taste</li>
               </ul>
                </v-col>
                <v-col md="6">
                  <input type="checkbox" id="checkbox" v-model="checked1">
                  <label for="checkbox">{{ checked1 }}</label>
                    
                </v-col>
              </v-row>
              <v-row>
                <v-col md="6">
                   
                  <p>3. Have you had close contact with any confirmed/ suspected COVID-19 cases within the last 14 days? </p>
                
                </v-col>
                <v-col md="6">
                  <input type="checkbox" id="checkbox" v-model="checked2">
                  <label for="checkbox">{{ checked2 }}</label>
                    
                </v-col>
              </v-row>
            </v-form>
          </div>
          <span>Suggested Test: {{test}}</span>
          <v-card-actions class="pa-4">
            <v-spacer></v-spacer>
            <v-btn :disabled="!valid" @click="submit">Submit</v-btn>
          </v-card-actions>
        </v-card>
      </v-col>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      customerForm: null,
      valid: false,
      selectedChoice: [],
      checked:false,
      checked1:false,
      checked2:false,
      test: ""
      
    };
  },
  methods:{
    logout() {
      this.$router.push({
        path: "/login",
      });
    },
    submit(){
      if (!this.checked1 && !this.checked && this.checked2){
        this.test = "RAT";
        
      }
      else if(this.checked1 || this.checked){
        this.test = "PCR";
      }
      else {
        this.test = "A test is not needed";
      }
      
      
      
    }

  }
};
</script>

<style></style>
