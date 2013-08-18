/*
 * Copyright 2013 Inmite s.r.o. (www.inmite.eu).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package eu.inmite.demo.dialogs;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Toast;

import eu.inmite.android.lib.dialogs.ISimpleDialogCancelListener;
import eu.inmite.android.lib.dialogs.ISimpleDialogListener;
import eu.inmite.android.lib.dialogs.ProgressDialogFragment;
import eu.inmite.android.lib.dialogs.SimpleDialogFragment;

public class MyActivity extends Activity implements
	ISimpleDialogListener,
	IFavoriteCharacterDialogListener,
	ISimpleDialogCancelListener {

	public static final int THEME_DEFAULT_DARK = 0;
	public static final int THEME_DEFAULT_LIGHT = 1;
	public static final int THEME_CUSTOM_DARK = 2;
	public static final int THEME_CUSTOM_LIGHT = 3;
	public static final String EXTRA_THEME = "theme";

	private static final int REQUEST_PROGRESS = 1;

	MyActivity c = this;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		setThemeOnCreate();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		findViewById(R.id.message_dialog).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				SimpleDialogFragment.createBuilder(c, getFragmentManager()).setMessage(R.string.message_1).show();
			}
		});
		findViewById(R.id.message_title_dialog).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				SimpleDialogFragment.createBuilder(c, getFragmentManager()).setTitle(R.string.title).setMessage(R.string.message_2).show();
			}
		});
		findViewById(R.id.message_title_buttons_dialog).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				SimpleDialogFragment.createBuilder(c, getFragmentManager())
						.setTitle(R.string.title)
						.setMessage(R.string.message_3)
						.setPositiveButtonText(R.string.positive_button)
						.setNegativeButtonText(R.string.negative_button).setRequestCode(42)
						.setTag("custom-tag")
						.show();
			}
		});
		findViewById(R.id.progress_dialog).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ProgressDialogFragment.createBuilder(c, getFragmentManager())
						.setMessage(R.string.message_4)
						.setRequestCode(REQUEST_PROGRESS)
						.setTitle(R.string.app_name)
						.show();
			}
		});
		/*
		List dialog is not styled - removing for now, TODO: add it to the library
		findViewById(R.id.list_dialog).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				FavoriteCharacterDialogFragment.show(c, "Your favorite character (some text added to make it longer):", new String[]{"Jayne", "Malcolm", "Kaylee",
					"Wash", "Zoe", "River"});
			}
		});*/
		findViewById(R.id.custom_dialog).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				JayneHatDialogFragment.show(c);
			}
		});
		findViewById(R.id.default_dark_theme).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				setCurrentTheme(THEME_DEFAULT_DARK);
			}
		});
		findViewById(R.id.default_light_theme).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				setCurrentTheme(THEME_DEFAULT_LIGHT);
			}
		});
		findViewById(R.id.custom_dark_theme).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				setCurrentTheme(THEME_CUSTOM_DARK);
			}
		});
		findViewById(R.id.custom_light_theme).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				setCurrentTheme(THEME_CUSTOM_LIGHT);
			}
		});
	}

	@Override
	public void onListItemSelected(String value, int number) {
		Toast.makeText(c, "Selected: " + value, Toast.LENGTH_SHORT).show();
	}

	// ISimpleDialogCancelListener

	@Override
	public void onCancelled(int requestCode) {
		if (requestCode == 42) {
			Toast.makeText(c, "Dialog cancelled", Toast.LENGTH_SHORT).show();
		} else if (requestCode == REQUEST_PROGRESS) {
			Toast.makeText(c, "Progress dialog cancelled", Toast.LENGTH_SHORT).show();
		}
	}

	// ISimpleDialogListener

	@Override
	public void onPositiveButtonClicked(int requestCode) {
		if (requestCode == 42) {
			Toast.makeText(c, "Positive button clicked", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onNegativeButtonClicked(int requestCode) {
		if (requestCode == 42) {
			Toast.makeText(c, "Negative button clicked", Toast.LENGTH_SHORT).show();
		}
	}

	private void setCurrentTheme(int theme) {
		Intent i = new Intent(c, MyActivity.class);
		i.putExtra(EXTRA_THEME, theme);
		startActivity(i);
		finish();
		overridePendingTransition(0, 0);
	}

	private void setThemeOnCreate() {
		int theme = getIntent().getIntExtra(EXTRA_THEME, THEME_CUSTOM_DARK);
		switch (theme) {
			case THEME_DEFAULT_DARK:
				setTheme(R.style.DefaultDarkTheme);
				break;
			case THEME_DEFAULT_LIGHT:
				setTheme(R.style.DefaultLightTheme);
				break;
			case THEME_CUSTOM_DARK:
				setTheme(R.style.CustomDarkTheme);
				break;
			case THEME_CUSTOM_LIGHT:
				setTheme(R.style.CustomLightTheme);
				break;
		}
	}
}
