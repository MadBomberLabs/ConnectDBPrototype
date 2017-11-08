package madbomberlabs.com.connectdbprototype;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.view.View.GONE;

public class MainActivity extends AppCompatActivity {

    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;

    ProgressBar progressBar;
    ListView listView;
    Button buttonAddUpdate;

    List<Org> orgList;

    boolean isUpdating = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);
        listView = findViewById(R.id.listViewHeroes);

        orgList = new ArrayList<>();

        // FILL LISTVIEW WITH ITEMS FROM DATABASE
        readHeroes();
    }

/* NOT USED IN THIS INSTANCE
    private void createHero() {
        String name = editTextName.getText().toString().trim();
        String realname = editTextRealname.getText().toString().trim();

        int rating = (int) ratingBar.getRating();

        String team = spinnerTeam.getSelectedItem().toString();

        if (TextUtils.isEmpty(name)) {
            editTextName.setError("Please enter name");
            editTextName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(realname)) {
            editTextRealname.setError("Please enter real name");
            editTextRealname.requestFocus();
            return;
        }

        HashMap<String, String> params = new HashMap<>();
        params.put("name", name);
        params.put("realname", realname);
        params.put("rating", String.valueOf(rating));
        params.put("teamaffiliation", team);

        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_CREATE_ORG, params, CODE_POST_REQUEST);
        request.execute();
    }
*/

    private void readHeroes() {
        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_READ_ORG, null, CODE_GET_REQUEST);
        request.execute();
    }

/* NOT USED IN THIS INSTANCE
    private void updateHero() {
        String id = editTextHeroId.getText().toString();
        String name = editTextName.getText().toString().trim();
        String realname = editTextRealname.getText().toString().trim();

        int rating = (int) ratingBar.getRating();

        String team = spinnerTeam.getSelectedItem().toString();


        if (TextUtils.isEmpty(name)) {
            editTextName.setError("Please enter name");
            editTextName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(realname)) {
            editTextRealname.setError("Please enter real name");
            editTextRealname.requestFocus();
            return;
        }

        HashMap<String, String> params = new HashMap<>();
        params.put("id", id);
        params.put("name", name);
        params.put("realname", realname);
        params.put("rating", String.valueOf(rating));
        params.put("teamaffiliation", team);


        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_UPDATE_ORG, params, CODE_POST_REQUEST);
        request.execute();

        buttonAddUpdate.setText("Add");

        editTextName.setText("");
        editTextRealname.setText("");
        ratingBar.setRating(0);
        spinnerTeam.setSelection(0);

        isUpdating = false;
    }
*/

/* NOT USED IN THIS INSTANCE
    private void deleteHero(int id) {
        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_DELETE_ORG + id, null, CODE_GET_REQUEST);
        request.execute();
    }
*/

    private void refreshOrgList(JSONArray heroes) throws JSONException {
        orgList.clear();

        for (int i = 0; i < heroes.length(); i++) {
            JSONObject obj = heroes.getJSONObject(i);

            orgList.add(new Org(
                    obj.getString("id"),
                    obj.getString("org"),
                    obj.getString("firstName"),
                    obj.getString("lastName"),
                    obj.getString("email"),
                    obj.getString("phone"),
                    obj.getString("website"),
                    obj.getString("serviceType"),
                    obj.getString("service"),
                    obj.getString("streetAddress"),
                    obj.getString("city"),
                    obj.getString("state"),
                    obj.getString("zip"),
                    obj.getString("favorite")
            ));
        }

        OrgAdapter adapter = new OrgAdapter(orgList);
        listView.setAdapter(adapter);
    }

    private class PerformNetworkRequest extends AsyncTask<Void, Void, String>
    {
        String url;
        HashMap<String, String> params;
        int requestCode;

        PerformNetworkRequest(String url, HashMap<String, String> params, int requestCode)
        {
            this.url = url;
            this.params = params;
            this.requestCode = requestCode;
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String s)
        {
            super.onPostExecute(s);
            progressBar.setVisibility(GONE);
            try
            {
                JSONObject object = new JSONObject(s);
                if (!object.getBoolean("error"))
                {
                    Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
                    refreshOrgList(object.getJSONArray("orgs"));
                }
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(Void... voids)
        {
            RequestHandler requestHandler = new RequestHandler();

            if (requestCode == CODE_POST_REQUEST)
                return requestHandler.sendPostRequest(url, params);


            if (requestCode == CODE_GET_REQUEST)
                return requestHandler.sendGetRequest(url);

            return null;
        }
    }

    class OrgAdapter extends ArrayAdapter<Org>
    {
        List<Org> orgList;

        public OrgAdapter(List<Org> orgList)
        {
            super(MainActivity.this, R.layout.layout_org_list, orgList);
            this.orgList = orgList;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            LayoutInflater inflater = getLayoutInflater();
            View listViewItem = inflater.inflate(R.layout.layout_org_list, null, true);

            return listViewItem;
        }
    }
}
